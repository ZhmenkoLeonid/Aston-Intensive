package com.zhmenko.author.data.mapper;

import com.google.inject.Inject;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.book.data.dao.BookDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorResultSetMapper {
    private final BookDao bookDao;

    @Inject
    public AuthorResultSetMapper(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public AuthorEntity map(ResultSet resultSet) throws SQLException {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(resultSet.getInt("id"));
        authorEntity.setFirstName(resultSet.getString("first_name"));
        authorEntity.setSecondName(resultSet.getString("second_name"));
        authorEntity.setThirdName(resultSet.getString("third_name"));

        authorEntity.setBookEntities(bookDao.selectBooksByAuthorId(authorEntity.getId()));
        return authorEntity;
    }
}
