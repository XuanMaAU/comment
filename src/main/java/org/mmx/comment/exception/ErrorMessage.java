package org.mmx.comment.exception;

import lombok.Getter;

/**
 * Detailed error message
 */
@Getter
public class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
