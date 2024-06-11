package com.zhmenko.user_book.data.dao;

import com.google.inject.Inject;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.database.connection.ConnectionManager;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.SQLRuntimeException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserBookDaoImpl class provides methods to manage the relationship between users and books.
 */
public class UserBookDaoImpl implements UserBookDao {
    private static final Logger log = LogManager.getLogger(UserBookDaoImpl.class);
    private static final String DELETE_USER_BOOKS_BY_USER_ID_AND_BOOKS_IDS = """
            DELETE FROM books_users
            WHERE book_id in (%s) AND user_id = ?
            """;

    private static final String DELETE_USER_BOOKS_BY_USER_ID = """
            DELETE FROM books_users
            WHERE user_id = ?
            """;

    private static final String DELETE_USER_BOOKS_BY_BOOK_ID = """
            DELETE FROM books_users
            WHERE book_id = ?
            """;

    private static final String INSERT_USER_BOOK = "INSERT INTO books_users (user_id, book_id) VALUES (?, ?)";
    private final ConnectionManager connectionManager;
    private final UserDao userDao;
    private final BookDao bookDao;

    /**
     * Constructor for UserBookDaoImpl.
     *
     * @param connectionManager Data source instance for interacting with the database.
     * @param userDao           UserDao instance for interacting with the users table.
     * @param bookDao           BookDao instance for interacting with the books table.
     */
    @Inject
    public UserBookDaoImpl(final ConnectionManager connectionManager, final UserDao userDao, final BookDao bookDao) {
        this.connectionManager = connectionManager;
        this.userDao = userDao;
        this.bookDao = bookDao;
    }

    /**
     * Add specified books to be owned by user.
     *
     * @param booksId books ids that will be owned by user after query
     * @param userId  user id to which books will be added
     * @return true if database has been changed
     */
    @Override
    public boolean insertUserBooksByUserId(final Collection<Integer> booksId, final int userId) {
        checkParameters(booksId, userId);
        // find if user already have links to books
        final Set<BookEntity> bookEntities = bookDao.selectBooksByUserId(userId);
        final Set<Integer> old = bookEntities.stream().map(BookEntity::getId).collect(Collectors.toSet());
        Set<Integer> intersection = new HashSet<>(booksId);
        intersection.retainAll(old);
        if (!intersection.isEmpty()) {
            throw new BadRequestException(MessageFormat
                    .format("user already have link to book(s) with id ({0})",
                            StringUtils.collectionToSingleString(intersection, ", ")));
        }

        if (booksId.isEmpty()) return false;
        boolean rowInserted = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_BOOK);) {
            log.info(INSERT_USER_BOOK);
            statement.setInt(1, userId);
            for (int bookId : booksId) {
                statement.setInt(2, bookId);
                statement.addBatch();
            }
            int[] rowInsertedCntArray = statement.executeBatch();
            for (int i : rowInsertedCntArray) {
                if (i > 0) {
                    rowInserted = true;
                    break;
                }
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return rowInserted;
    }

    /**
     * Delete specified books owned by user.
     *
     * @param booksId ids that will be not more owned by user
     * @param userId  user id from which books will be deleted
     * @return true if database has been changed
     */
    @Override
    public boolean deleteUserBooksByUserId(final Collection<Integer> booksId, final int userId) {
        if (booksId.isEmpty()) return false;
        checkParameters(booksId, userId);
        // find if user don't have links to books
        final Set<BookEntity> bookEntities = bookDao.selectBooksByUserId(userId);
        final Set<Integer> old = bookEntities.stream().map(BookEntity::getId).collect(Collectors.toSet());
        Set<Integer> intersection = new HashSet<>(booksId);
        intersection.removeAll(old);
        if (!intersection.isEmpty()) {
            throw new BadRequestException(MessageFormat
                    .format("User dont have link to book(s) with id ({0})",
                            StringUtils.collectionToSingleString(intersection, ", ")));
        }
        String booksIdString = StringUtils.collectionToSingleString(booksId, ", ");
        String query = String.format(DELETE_USER_BOOKS_BY_USER_ID_AND_BOOKS_IDS, booksIdString);
        return deleteById(userId, query);
    }

    /**
     * Deletes all books owned by the specified user.
     *
     * @param userId the id of the user whose owned books will be deleted
     * @return true if the database has been changed, false otherwise
     * @throws UserNotFoundException if the user does not exist
     */
    @Override
    public boolean deleteUserBooksByUserId(final int userId) {
        if (!userDao.isExistById(userId)) throw new UserNotFoundException(userId);
        return deleteById(userId, DELETE_USER_BOOKS_BY_USER_ID);
    }

    /**
     * Deletes all books with specified if from users
     *
     * @param bookId the id of the book to be deleted from users
     * @return true if the database has been changed, false otherwise
     * @throws BookNotFoundException if book does not exist
     */
    @Override
    public boolean deleteUserBooksByBookId(final int bookId) {
        if (!bookDao.isExistById(bookId)) throw new BookNotFoundException(bookId);
        return deleteById(bookId, DELETE_USER_BOOKS_BY_BOOK_ID);
    }

    /**
     * Checks if the specified parameters are valid.
     *
     * @param booksId the ids of the books
     * @param userId  the id of the user
     */
    private void checkParameters(final Collection<Integer> booksId, final int userId) {
        if (!userDao.isExistById(userId)) throw new UserNotFoundException(userId);
        if (!bookDao.isBooksExistById(booksId))
            throw new BookNotFoundException(MessageFormat
                    .format("At least one book with id from array {1} dont exist!",
                            userId, booksId));
    }

    /**
     * Deletes a record from the database based on the specified id and query.
     *
     * @param id          the id of the record to be deleted
     * @param deleteQuery the SQL query to delete the record
     * @return true if the database has been changed, false otherwise
     */
    private boolean deleteById(int id, final String deleteQuery) {
        boolean rowDeleted;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery);) {
            log.info(deleteQuery);
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return rowDeleted;
    }
}
