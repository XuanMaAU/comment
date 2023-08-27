package org.mmx.comment.exception;

/**
 * Base application exception
 */
public class AppException extends RuntimeException {
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
