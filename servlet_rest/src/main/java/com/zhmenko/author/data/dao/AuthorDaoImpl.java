package com.zhmenko.author.data.dao;

import com.google.inject.Inject;
import com.zhmenko.author.data.mapper.AuthorResultSetMapper;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.connection.ConnectionManager;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.SQLRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDao {
    private static final Logger log = LogManager.getLogger(AuthorDaoImpl.class);

    private static final String INSERT_AUTHOR = "INSERT INTO authors (first_name, second_name, third_name) VALUES (?, ?, ?)";
    private static final String UPDATE_AUTHOR = "UPDATE authors SET first_name = ?, second_name = ?, third_name = ? WHERE id = ?";
    private static final String DELETE_AUTHOR = "DELETE FROM authors WHERE id = ?";
    private static final String SELECT_AUTHOR_BY_ID =
            """
                    SELECT id, first_name, second_name, third_name
                    FROM authors a
                    WHERE id = ?
                    """;
    private static final String COUNT_AUTHOR_BY_ID = """
            SELECT count(*) count
            FROM authors a
            WHERE a.id = ?
            """;

    private final ConnectionManager connectionManager;
    private final BookDao bookDao;

    private final AuthorResultSetMapper authorEntityResultSetMapper;

    @Inject
    public AuthorDaoImpl(final ConnectionManager connectionManager, final BookDao bookDao) {
        this.connectionManager = connectionManager;
        this.bookDao = bookDao;
        this.authorEntityResultSetMapper = new AuthorResultSetMapper(bookDao);
    }

    @Override
    public boolean insertAuthor(final AuthorEntity author) {
        boolean result;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR)) {
            int idx = 0;
            preparedStatement.setString(++idx, author.getFirstName());
            preparedStatement.setString(++idx, author.getSecondName());
            preparedStatement.setString(++idx, author.getThirdName());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean isExistById(final int id) {
        int count;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_AUTHOR_BY_ID);) {
            preparedStatement.setInt(1, id);
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
    public Optional<AuthorEntity> selectAuthorById(final int id) {
        AuthorEntity authorEntity;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID);) {
            preparedStatement.setInt(1, id);
            log.info(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new AuthorNotFoundException(id);
            authorEntity = authorEntityResultSetMapper.map(resultSet);
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return Optional.ofNullable(authorEntity);
    }

    @Override
    public boolean updateAuthor(final AuthorEntity authorEntity, final int id) {
        // if author don't exist - throw exception
        if (!isExistById(id)) throw new AuthorNotFoundException(id);
        boolean rowUpdated;
        // update author
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statementAuthorUpdate = connection.prepareStatement(UPDATE_AUTHOR);) {
            int idx = 0;
            statementAuthorUpdate.setString(++idx, authorEntity.getFirstName());
            statementAuthorUpdate.setString(++idx, authorEntity.getSecondName());
            statementAuthorUpdate.setString(++idx, authorEntity.getThirdName());
            statementAuthorUpdate.setInt(++idx, authorEntity.getId());
            rowUpdated = statementAuthorUpdate.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return rowUpdated;
    }

    @Override
    public boolean deleteAuthorById(final int id) {
        if (!isExistById(id)) throw new AuthorNotFoundException(id);
        boolean rowDeleted;
        // delete books associated with author
        bookDao.deleteBooksByAuthorId(id);

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_AUTHOR);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return rowDeleted;
    }
}
