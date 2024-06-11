package com.zhmenko.book.validator;

import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;

public class BookValidator {
    public boolean validate(final BookInsertRequest bookInsertRequest) {
        final String bookName = bookInsertRequest.getName();
        return bookInsertRequest.getAuthorId() >= 0 && bookName != null && !bookName.isEmpty();
    }

    public boolean validate(final BookModifyRequest bookModifyRequest, final int id) {
        final String bookName = bookModifyRequest.getName();
        return id >= 0 && bookName != null && !bookName.isEmpty() && bookModifyRequest.getId() == id;
    }
}
