package com.zhmenko.user.data.mapper;

import com.google.inject.Inject;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.user.data.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapper {
    private final BookDao bookDao;

    @Inject
    public UserResultSetMapper(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public UserEntity map(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getInt("id"));
        userEntity.setName(resultSet.getString("name"));
        userEntity.setCountry(resultSet.getString("country"));
        userEntity.setEmail(resultSet.getString("email"));
        userEntity.setBookEntitySet(bookDao.selectBooksByUserId(userEntity.getId()));
        return userEntity;
    }

    public UserEntity getUserByBookId(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getInt("id"));
        userEntity.setName(resultSet.getString("name"));
        userEntity.setEmail(resultSet.getString("email"));
        userEntity.setCountry(resultSet.getString("country"));
        return userEntity;
    }
}
