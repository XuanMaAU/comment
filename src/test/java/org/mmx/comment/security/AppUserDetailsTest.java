package org.mmx.comment.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mmx.comment.domain.User;
import org.mmx.comment.domain.UserRole;

/**
 * Unit test cases for AppUserDetails
 */
public class AppUserDetailsTest {
    @Test
    public void testAuthorities() {
        // given:
        User user = new User(1l, "user", "test_password", Arrays.asList(new UserRole(1l, 1l, SecurityRole.USER)));

        // when:
        AppUserDetails details = new AppUserDetails(user);

        // then:
        assertEquals(user, details.getUser());
        assertEquals(Arrays.asList("USER"),
                     details.getAuthorities().stream()
                     .map(auth -> auth.getAuthority())
                     .collect(Collectors.toList())
                     );
    }
}
