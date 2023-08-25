package org.mmx.comment.security;

import java.util.List;
import java.util.stream.Collectors;

import org.mmx.comment.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Load user by name: {}", username);

        org.mmx.comment.domain.User appUser = userService.findByName(username);
        log.debug("Loaded User = {}", appUser);

        List<GrantedAuthority> authorities = appUser.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.toString()))
            .collect(Collectors.toList());

        log.debug("Authorities = {}", authorities);

        User user = new User(appUser.getName(), appUser.getHashedPassword(), authorities);

        return user;
    }
}
