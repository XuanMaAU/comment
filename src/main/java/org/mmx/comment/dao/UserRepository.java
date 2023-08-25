package org.mmx.comment.dao;

import java.util.Optional;

import org.mmx.comment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Database repository for users
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by name
     *
     * @param name the user name to find from
     *
     * @return the user entity
     */
    Optional<User> findByName(String name);
}
