package org.mmx.comment.exception;

import lombok.Getter;

/**
 * User not found exception
 */
@Getter
public class UserNotFoundException extends UserException {
    public UserNotFoundException(Long id, String name) {
        this(id, name, null);
    }

    public UserNotFoundException(Long id, String name, Throwable cause) {
        super(id, name, "User with id " + id + " not found", cause);
    }
}
