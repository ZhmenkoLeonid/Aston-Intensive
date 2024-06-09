package com.zhmenko.book.data.mapper;

import com.google.inject.Inject;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.user.data.dao.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookResultSetMapper {
    private final UserDao userDao;
    private final AuthorDao authorDao;

    @Inject
    public BookResultSetMapper(final UserDao userDao, final AuthorDao authorDao) {
        this.userDao = userDao;
        this.authorDao = authorDao;
    }

    public BookEntity map(final ResultSet resultSet) throws SQLException {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(resultSet.getInt("id"));
        bookEntity.setName(resultSet.getString("name"));
        int authorId = resultSet.getInt("author_id");
        bookEntity.setAuthor(authorDao.selectAuthorById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId)));
        bookEntity.setUserEntities(userDao.selectUsersByBookId(bookEntity.getId()));
        return bookEntity;
    }

    public BookEntity getBooksById(final ResultSet resultSet) throws SQLException {
        BookEntity book = new BookEntity();
        book.setId(resultSet.getInt("id"));
        book.setName(resultSet.getString("name"));
        return book;
    }
}
