package com.zhmenko.book.data.dao;

import com.google.inject.Inject;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.book.data.mapper.BookResultSetMapper;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.database.connection.ConnectionManager;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.SQLRuntimeException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.user_book.data.dao.UserBookDao;
import com.zhmenko.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Dao for user CRUD operations
 */
public class BookDaoImpl implements BookDao {
    private static final Logger log = LogManager.getLogger(BookDaoImpl.class);
    private static final String SELECT_BOOK_BY_ID = "SELECT id, name, author_id FROM books WHERE id = ?";
    private static final String INSERT_BOOK = "INSERT INTO books (name, author_id) VALUES (?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET name = ?, author_id= ? WHERE id = ?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";
    private static final String COUNT_BOOK_BY_ID = """
            SELECT count(*) count
            FROM books b
            WHERE b.id = ?
            """;
    private static final String COUNT_BOOKS_BY_ID = """
            SELECT count(*) count
            FROM books b
            WHERE b.id in (%s)
            """;
    private static final String SELECT_BOOKS_BY_AUTHOR_ID =
            """
                    SELECT id, name, author_id
                    FROM books
                    WHERE author_id = ?
                            """;

    private static final String SELECT_BOOKS_BY_USER_ID = """
            SELECT id, name, user_id
            FROM books b
                INNER JOIN books_users bu ON ( b.id = bu.book_id  )
            WHERE bu.user_id = ?
            """;

    private final ConnectionManager connectionManager;

    private final BookResultSetMapper bookEntityResultSetMapper;

    private final AuthorDao authorDao;

    private final UserBookDao userBookDao;
    private final UserDao userDao;

    /**
     * Initializes the {@code BookDaoImpl} with the provided dependencies.
     *
     * @param connectionManager the {@link ConnectionManager} to be used for database operations
     * @param userDao           the {@link UserDao} to be used for user-related operations
     * @param authorDao         the {@link AuthorDao} to be used for author-related operations
     * @param userBookDao       the {@link UserBookDao} to be used for user-book-related operations
     */
    @Inject
    public BookDaoImpl(final ConnectionManager connectionManager,
                       final UserDao userDao,
                       final AuthorDao authorDao,
                       final UserBookDao userBookDao) {
        this.connectionManager = connectionManager;
        this.authorDao = authorDao;
        this.userBookDao = userBookDao;
        this.userDao = userDao;
        this.bookEntityResultSetMapper = new BookResultSetMapper(userDao, authorDao);
    }

    /**
     * Inserts a new book into the database.
     *
     * @param bookEntity the {@link BookEntity} to be inserted
     * @return true if the insertion was successful, false otherwise
     * @throws AuthorNotFoundException if the author of the book does not exist in the database
     * @throws SQLRuntimeException     if an error occurs during the database operation
     */
    public boolean insertBook(final BookEntity bookEntity) {
        if (!authorDao.isExistById(bookEntity.getAuthor().getId()))
            throw new AuthorNotFoundException(bookEntity.getAuthor().getId());
        boolean result;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK)) {
            int idx = 0;
            preparedStatement.setString(++idx, bookEntity.getName());
            preparedStatement.setInt(++idx, bookEntity.getAuthor().getId());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * Retrieves a book by its id from the database.
     *
     * @param id the id of the book to be retrieved
     * @return an {@link Optional} containing the {@link BookEntity} if found, otherwise an empty {@link Optional}
     * @throws SQLRuntimeException if an error occurs during the database operation
     */
    public Optional<BookEntity> selectBookById(final int id) {
        BookEntity bookEntity = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID);) {
            preparedStatement.setInt(1, id);
            log.info(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) return Optional.empty();
            bookEntity = bookEntityResultSetMapper.map(resultSet);
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return Optional.ofNullable(bookEntity);
    }

    /**
     * Retrieves all books associated with a specific author.
     *
     * @param authorId the id of the author whose books should be retrieved
     * @return a set of {@link BookEntity} objects if found, otherwise an empty set
     * @throws SQLRuntimeException     if an error occurs during the database operation
     * @throws AuthorNotFoundException if author not exist
     */
    @Override
    public Set<BookEntity> selectBooksByAuthorId(final int authorId) {
        if (!authorDao.isExistById(authorId)) throw new AuthorNotFoundException(authorId);
        Set<BookEntity> bookEntitySet = new HashSet<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_AUTHOR_ID);) {
            preparedStatement.setInt(1, authorId);
            log.info(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                bookEntitySet.add(bookEntityResultSetMapper.getBooksById(resultSet));
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return bookEntitySet;
    }

    /**
     * Retrieves all books associated with a specific user.
     *
     * @param userId the id of the user whose books should be retrieved
     * @return a set of {@link BookEntity} objects if found, otherwise an empty set
     * @throws SQLRuntimeException if an error occurs during the database operation
     */
    @Override
    public Set<BookEntity> selectBooksByUserId(final int userId) {
        if (!userDao.isExistById(userId)) throw new UserNotFoundException(userId);
        Set<BookEntity> bookEntitySet = new HashSet<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_USER_ID);) {
            preparedStatement.setInt(1, userId);
            log.info(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                bookEntitySet.add(bookEntityResultSetMapper.getBooksById(resultSet));
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return bookEntitySet;
    }

    /**
     * Checks if a book with the given id exists in the database.
     *
     * @param bookId the id of the book to be checked
     * @return true if the book exists, false otherwise
     * @throws SQLRuntimeException if an error occurs during the database operation
     */
    public boolean isExistById(final int bookId) {
        int count;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BOOK_BY_ID);) {
            preparedStatement.setInt(1, bookId);
            log.info(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("count");
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return count == 1;
    }

    @Override
    public boolean isBooksExistById(final Collection<Integer> booksId) {
        if (booksId.isEmpty()) return true;
        int count;
        String booksIdString = StringUtils.collectionToSingleString(booksId, ", ");
        String query = String.format(COUNT_BOOKS_BY_ID, booksIdString);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            log.info(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("count");
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return count == booksId.size();
    }

    /**
     * Updates a book in the database.
     *
     * @param bookEntity the {@link BookEntity} to be updated
     * @param id         the id of the book to be updated
     * @return true if the update was successful, false otherwise
     * @throws AuthorNotFoundException if the author of the book does not exist in the database
     * @throws SQLRuntimeException     if an error occurs during the database operation
     */
    public boolean updateBook(final BookEntity bookEntity, final int id) {
        if (!authorDao.isExistById(bookEntity.getAuthor().getId()))
            throw new AuthorNotFoundException(bookEntity.getAuthor().getId());
        if (!isExistById(id)) throw new BookNotFoundException(id);
        boolean rowUpdated;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statementBookUpdate = connection.prepareStatement(UPDATE_BOOK);) {
            int idx = 0;
            statementBookUpdate.setString(++idx, bookEntity.getName());
            statementBookUpdate.setInt(++idx, bookEntity.getAuthor().getId());
            statementBookUpdate.setInt(++idx, bookEntity.getId());
            rowUpdated = statementBookUpdate.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return rowUpdated;
    }

    /**
     * Deletes a book by its id from the database.
     *
     * @param id the id of the book to be deleted
     * @return true if the deletion was successful, false otherwise
     * @throws SQLRuntimeException if an error occurs during the database operation
     */
    public boolean deleteBookById(final int id) {
        if (!isExistById(id)) throw new BookNotFoundException(id);
        boolean rowDeleted;
        // delete books links in the link table books_users
        userBookDao.deleteUserBooksByBookId(id);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return rowDeleted;
    }

    /**
     * Deletes all books associated with a specific author.
     *
     * @param authorId the id of the author whose books should be deleted
     * @return true if all books were successfully deleted, false otherwise
     * @throws AuthorNotFoundException if author not exist
     */
    @Override
    public boolean deleteBooksByAuthorId(final int authorId) {
        if (!authorDao.isExistById(authorId)) throw new AuthorNotFoundException(authorId);
        boolean result = true;
        final Set<BookEntity> bookEntities = selectBooksByAuthorId(authorId);
        for (final BookEntity bookEntity : bookEntities) {
            result &= deleteBookById(bookEntity.getId());
        }
        return result;
    }
}
