package com.zhmenko.book.data.dao;

import com.zhmenko.book.data.model.BookEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface BookDao {
    boolean insertBook(final BookEntity book);

    Optional<BookEntity> selectBookById(final int id);

    boolean updateBook(final BookEntity user, final int id);

    boolean deleteBookById(final int id);

    boolean deleteBooksByAuthorId(int authorId);

    Set<BookEntity> selectBooksByAuthorId(final int authorId);

    Set<BookEntity> selectBooksByUserId(final int userId);

    boolean isExistById(final int bookId);

    boolean isBooksExistById(final Collection<Integer> booksId);
}
