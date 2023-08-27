package org.mmx.comment.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mmx.comment.domain.User;
import org.mmx.comment.domain.UserRole;
import org.mmx.comment.security.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Unit test cases for UserRepository
 */
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    private User expUser = new User(152l, "newUser", "testPassword", Arrays.asList(new UserRole(102l, 152l, SecurityRole.USER)));

    @Test
    public void testFindByName_success() {
        // when:
        Optional<User> user = repo.findByName("newUser");

        // then:
        assertTrue(user.isEmpty());

        // given:
        repo.save(expUser);

        // when:
        user = repo.findByName("newUser");

        // then:
        assertEquals(expUser, user.get());
    }

    @Test
    public void testFindByName_notFound() {
        // when:
        Optional<User> user = repo.findByName("nonExistingUser");

        // then:
        assertTrue(user.isEmpty());
    }
}
