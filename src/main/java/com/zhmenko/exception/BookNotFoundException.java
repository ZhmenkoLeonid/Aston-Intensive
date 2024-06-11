package com.zhmenko.exception;

import java.text.MessageFormat;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(int id) {
        super(MessageFormat.format("Book with id {0} not found!", id));
    }

    public BookNotFoundException(final String message) {
        super(message);
    }
}
