package com.zhmenko.user_book.validator;

import com.zhmenko.user_book.model.UserBookRequest;

import java.util.Collection;
import java.util.List;

public class UserBookValidator {
    public boolean validate(final UserBookRequest userBookRequest) {
        final List<Integer> booksId = userBookRequest.getBooksId();
        return userBookRequest.getUserId() >= 0 &&
               booksId != null && !booksId.isEmpty() && !hasDuplicates(userBookRequest.getBooksId());
    }

    private boolean hasDuplicates(Collection<?> objects) {
        return objects.stream().distinct().count() != objects.size();
    }
}
