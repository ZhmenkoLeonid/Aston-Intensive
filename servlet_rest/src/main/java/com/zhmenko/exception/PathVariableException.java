package com.zhmenko.exception;

/**
 * Thrown when path variable problems occurred
 */
public class PathVariableException extends RuntimeException {
    public PathVariableException(String message) {
        super(message);
    }
}
