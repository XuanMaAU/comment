package org.mmx.comment.service;

import org.mmx.comment.domain.User;

/**
 * User service to manipulate users
 */
public interface UserService {
    /**
     * Find a user by name
     *
     * @param name the user name to find from
     *
     * @return the user entity
     *
     * @throws UserNotFoundException
     */
    User findByName(String name);
}
