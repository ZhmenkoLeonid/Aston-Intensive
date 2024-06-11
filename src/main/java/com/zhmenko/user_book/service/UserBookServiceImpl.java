package com.zhmenko.user_book.service;

import com.google.inject.Inject;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.user_book.data.dao.UserBookDao;
import com.zhmenko.user_book.model.UserBookRequest;
import com.zhmenko.user_book.validator.UserBookValidator;

/**
 * Provides methods to add and delete books from a user
 */
public class UserBookServiceImpl implements UserBookService {
    private final UserBookDao userBookDao;

    private final UserBookValidator userBookValidator;

    /**
     * Constructor for UserBookServiceImpl.
     *
     * @param userBookDao       UserBookDao instance.
     * @param userBookValidator UserBookValidator instance.
     */
    @Inject
    public UserBookServiceImpl(final UserBookDao userBookDao, final UserBookValidator userBookValidator) {
        this.userBookDao = userBookDao;
        this.userBookValidator = userBookValidator;
    }

    /**
     * Adds books to a user.
     *
     * @param request UserBookRequest instance containing the list of books and user ID.
     * @throws BadRequestException if the request is invalid.
     */
    @Override
    public void addBooksToUser(final UserBookRequest request) {
        if (!userBookValidator.validate(request))
            throw new BadRequestException("Invalid users books add request");
        userBookDao.insertUserBooksByUserId(request.getBooksId(), request.getUserId());
    }

    /**
     * Deletes a books from a user
     *
     * @param request UserBookRequest instance containing the list of books and user ID.
     * @throws BadRequestException if the request is invalid.
     */
    @Override
    public void deleteBookFromUser(final UserBookRequest request) {
        if (!userBookValidator.validate(request))
            throw new BadRequestException("Invalid users books delete request");
        userBookDao.deleteUserBooksByUserId(request.getBooksId(), request.getUserId());
    }
}
