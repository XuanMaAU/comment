package org.mmx.comment.exception;

import lombok.Getter;

/**
 * Comment not found exception
 */
@Getter
public class CommentNotFoundException extends CommentException {
    /**
     * The id of the missing comment object
     */
    private long id;

    public CommentNotFoundException(long id) {
        this(id, "Comment with id " + id + " not found", null);
    }

    public CommentNotFoundException(long id, String message, Throwable cause) {
        super(message, cause);
        this.id = id;
    }
}
