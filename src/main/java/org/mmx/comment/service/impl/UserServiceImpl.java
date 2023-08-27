package org.mmx.comment.service.impl;

import org.mmx.comment.dao.UserRepository;
import org.mmx.comment.domain.User;
import org.mmx.comment.exception.AppException;
import org.mmx.comment.exception.DatabaseException;
import org.mmx.comment.exception.UserNotFoundException;
import org.mmx.comment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * User Service implementation with database repository
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    /**
     * The database repository
     */
    private UserRepository repo;

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User findByName(String name) {
        log.debug("Find user by name \"{}\"", name);

        try {
            return repo
                .findByName(name)
                .orElseThrow(() -> new UserNotFoundException(null, name));
        } catch (AppException ae) {
            throw ae;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
