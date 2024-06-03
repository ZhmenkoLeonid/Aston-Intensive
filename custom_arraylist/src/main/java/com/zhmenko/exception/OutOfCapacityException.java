package com.zhmenko.exception;

/**
 * Custom exception, thrown when collection can't contain more elements than needed
 */
public class OutOfCapacityException extends RuntimeException {

    public OutOfCapacityException(String message) {
        super(message);
    }
}
