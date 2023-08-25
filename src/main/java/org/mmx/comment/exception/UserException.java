package org.mmx.comment.exception;

import lombok.Getter;

/**
 * User not found exception
 */
@Getter
public class UserException extends RuntimeException {
    private Long id;
    private String name;

    public UserException(Long id, String name, String message, Throwable cause) {
        super(message, cause);

        this.id = id;
        this.name = name;
    }

}
