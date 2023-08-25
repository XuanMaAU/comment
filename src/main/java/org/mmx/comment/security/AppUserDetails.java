package org.mmx.comment.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.mmx.comment.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Application user details for authentication and authorization
 */
@Slf4j
@ToString
public class AppUserDetails implements UserDetails {
    /**
     * The underlying user entity
     */
    private User user;
    /**
     * The authorities
     */
    private List<GrantedAuthority> authorities;

    public AppUserDetails(User user) {
        this.user = user;

        // cache the authorities
        authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
            .collect(Collectors.toList());

        log.debug("Authorities = {}", authorities);
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
