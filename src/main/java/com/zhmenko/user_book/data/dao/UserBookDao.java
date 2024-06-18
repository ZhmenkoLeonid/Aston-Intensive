package com.zhmenko.user_book.data.dao;

import java.util.Collection;

public interface UserBookDao {

    boolean insertUserBooksByUserId(final Collection<Integer> booksId, final int userId);

    boolean deleteUserBooksByUserId(final Collection<Integer> booksId, final int userId);

    boolean deleteUserBooksByBookId(final int bookId);

    boolean deleteUserBooksByUserId(final int userId);
}
