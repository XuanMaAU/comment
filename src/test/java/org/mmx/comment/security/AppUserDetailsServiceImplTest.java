package org.mmx.comment.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mmx.comment.AppTestBase;
import org.mmx.comment.domain.User;
import org.mmx.comment.domain.UserRole;
import org.mmx.comment.exception.UserNotFoundException;
import org.mmx.comment.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Unit test cases for AppUserDetailsServiceImpl
 */
public class AppUserDetailsServiceImplTest extends AppTestBase {
    @Mock
    private UserService userService;

    @InjectMocks
    private AppUserDetailsServiceImpl service;

    @Test
    public void testLoadUserByUsername_success() {
        // given:
        User expUser = new User(1l, "user", "test_password", Arrays.asList(new UserRole(1l, 1l, SecurityRole.USER)));
        doReturn(expUser).when(userService).findByName("user");

        // when:
        AppUserDetails details = (AppUserDetails)service.loadUserByUsername("user");

        // then:
        assertEquals(expUser, details.getUser());
        assertEquals(Arrays.asList("USER"),
                     details.getAuthorities().stream()
                     .map(auth -> auth.getAuthority())
                     .collect(Collectors.toList())
                     );

    }

    @Test
    public void testLoadUserByUsername_userNotFound() {
        // given:
        doThrow(new UserNotFoundException(1l, "user")).when(userService).findByName("user");

        // then:
        assertThrows(UsernameNotFoundException.class,
                     () -> {
                         AppUserDetails details = (AppUserDetails)service.loadUserByUsername("user");
                     });
    }
}
