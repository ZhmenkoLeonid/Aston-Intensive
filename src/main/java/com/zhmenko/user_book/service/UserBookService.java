package com.zhmenko.user_book.service;

import com.zhmenko.user_book.model.UserBookRequest;

public interface UserBookService {

    void addBooksToUser(final UserBookRequest request);

    void deleteBookFromUser(final UserBookRequest request);

}
