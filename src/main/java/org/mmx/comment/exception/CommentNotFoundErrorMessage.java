package org.mmx.comment.exception;

import lombok.Getter;

/**
 * Detailed error message for comment not found
 */
@Getter
public class CommentNotFoundErrorMessage extends ErrorMessage {
    /**
     * The comment id
     */
    private long commentId;

    public CommentNotFoundErrorMessage(long commentId, String message) {
        super(message);

        this.commentId = commentId;
    }
}
