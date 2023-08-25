package org.mmx.comment.exception;

/**
 * Generic exception for comments
 */
public class CommentException extends RuntimeException {
    public CommentException(String msg) {
        super(msg);
    }

    public CommentException(Throwable cause) {
        super(cause);
    }
}
