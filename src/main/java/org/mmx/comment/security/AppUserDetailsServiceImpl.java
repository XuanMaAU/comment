package org.mmx.comment.security;

import org.mmx.comment.domain.User;
import org.mmx.comment.exception.UserNotFoundException;
import org.mmx.comment.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * User details service implementation for the comments application
 */
@Slf4j
@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {
    /**
     * The user service to loader users / roles
     */
    private UserService userService;

    @Autowired
    public AppUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Load the user from database and return security user details
     *
     * @param username the username to find the user from
     *
     * @return the security user details loaded from database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Load user by name: {}", username);

        try {
            User user = userService.findByName(username);
            log.debug("Loaded User = {}", user);

            return new AppUserDetails(user);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(username, e);
        }
    }
}
