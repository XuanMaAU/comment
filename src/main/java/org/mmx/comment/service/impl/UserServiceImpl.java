package org.mmx.comment.service.impl;

import org.mmx.comment.dao.UserRepository;
import org.mmx.comment.domain.User;
import org.mmx.comment.exception.UserNotFoundException;
import org.mmx.comment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User Service implementation with database repository
 */
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
        return repo
            .findByName(name)
            .orElseThrow(() -> new UserNotFoundException(null, name));
    }
}
