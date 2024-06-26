package com.zhmenko.book.data.dao;

import com.zhmenko.book.data.model.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    BookEntity insertBook(final BookEntity book);

    Optional<BookEntity> selectBookById(final Long id);

    List<BookEntity> selectAll();

    BookEntity updateBook(final BookEntity user);

    BookEntity deleteBookById(final Long id);

    boolean isExistById(final Long bookId);
}
