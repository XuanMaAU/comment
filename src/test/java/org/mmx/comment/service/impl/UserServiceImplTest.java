package org.mmx.comment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mmx.comment.AppTestBase;
import org.mmx.comment.dao.UserRepository;
import org.mmx.comment.domain.User;
import org.mmx.comment.domain.UserRole;
import org.mmx.comment.exception.UserNotFoundException;
import org.mmx.comment.security.SecurityRole;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Unit test cases for UserServiceImpl
 */
public class UserServiceImplTest extends AppTestBase {
    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service;

    private User expUser = new User(1l, "admin", "hashedPassword", Arrays.asList(new UserRole(1l, 1l, SecurityRole.ADMIN)));

    @Test
    public void testFindByName_success() {
        // given:
        doReturn(Optional.of(expUser)).when(repo).findByName("admin");

        // when:
        User user = service.findByName("admin");

        // then:
        assertEquals(expUser, user);
    }

    @Test
    public void testFindByName_notFound() {
        // given:
        doReturn(Optional.empty()).when(repo).findByName("nonExistingUser");

        // when:
        assertThrows(UserNotFoundException.class, () -> service.findByName("nonExistingUser"));
    }
}
