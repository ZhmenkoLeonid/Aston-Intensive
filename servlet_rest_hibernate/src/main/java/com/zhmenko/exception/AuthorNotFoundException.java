package com.zhmenko.exception;

import java.text.MessageFormat;

public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException(Long id) {
        super(MessageFormat.format("Author with id {0} not found!", id));
    }
}
