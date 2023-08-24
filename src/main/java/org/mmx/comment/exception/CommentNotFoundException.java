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
        this(id, null);
        this.id = id;
    }

    public CommentNotFoundException(long id, Throwable cause) {
        super(cause);
        this.id = id;
    }
}
