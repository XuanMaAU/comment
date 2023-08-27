package org.mmx.comment.security.authorization;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mmx.comment.AppTestBase;
import org.mmx.comment.domain.Comment;
import org.mmx.comment.domain.User;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mmx.comment.security.AppUserDetails;
import org.mmx.comment.service.CommentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * Unit test cases for AppAuthorizer
 */
public class AppAuthorizerTest extends AppTestBase {
    @Mock
    private CommentService commentService;

    @Mock
    private MethodSecurityExpressionOperations root;

    @Mock
    private Authentication auth;

    @Mock
    private AppUserDetails userDetails;

    @Mock
    private User user;

    @Mock
    private Comment comment;

    @InjectMocks
    private AppAuthorizer authorizer;

    private long userId = 123l;
    private long otherUserId = 111l;
    private long commentId = 456l;

    @BeforeEach
    public void setup() {
        // these stubbings may not be called in all cases,
        // use lenient() to avoid Mockito exceptions
        lenient().doReturn(userId).when(user).getId();
        lenient().doReturn(user).when(userDetails).getUser();
        lenient().doReturn(userDetails).when(auth).getPrincipal();

        doReturn(auth).when(root).getAuthentication();
    }

    /* ======== canEdit ======== */

    @Test
    public void testCanEdit_admin_success() {
        // given:
        doReturn(true).when(root).hasAuthority("ADMIN");

        // then:
        assertTrue(authorizer.canEdit(root, commentId));
    }

    @Test
    public void testCanEdit_noRole() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(false).when(root).hasAuthority("USER");

        // then:
        assertFalse(authorizer.canEdit(root, commentId));
    }

    @Test
    public void testCanEdit_user_success() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(true).when(root).hasAuthority("USER");
        doReturn(comment).when(commentService).findById(commentId);
        doReturn(userId).when(comment).getUserId();

        // then:
        assertTrue(authorizer.canEdit(root, commentId));
    }

    @Test
    public void testCanEdit_user_notAuthor() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(true).when(root).hasAuthority("USER");
        doReturn(comment).when(commentService).findById(commentId);
        doReturn(otherUserId).when(comment).getUserId();

        // then:
        assertFalse(authorizer.canEdit(root, commentId));
    }

    @Test
    public void testCanEdit_user_commentNotFound() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(true).when(root).hasAuthority("USER");
        doThrow(new CommentNotFoundException(commentId)).when(commentService).findById(commentId);

        // then:
        assertThrows(CommentNotFoundException.class, () -> authorizer.canEdit(root, commentId));
    }

    /* ======== canDelete ======== */

    @Test
    public void testCanDelete_admin_success() {
        // given:
        doReturn(true).when(root).hasAuthority("ADMIN");

        // then:
        assertTrue(authorizer.canDelete(root, commentId));
    }

    @Test
    public void testCanDelete_noRole() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(false).when(root).hasAuthority("USER");

        // then:
        assertFalse(authorizer.canDelete(root, commentId));
    }

    @Test
    public void testCanDelete_user_success() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(true).when(root).hasAuthority("USER");
        doReturn(comment).when(commentService).findById(commentId);
        doReturn(userId).when(comment).getUserId();

        // then:
        assertTrue(authorizer.canDelete(root, commentId));
    }

    @Test
    public void testCanDelete_user_notAuthor() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(true).when(root).hasAuthority("USER");
        doReturn(comment).when(commentService).findById(commentId);
        doReturn(otherUserId).when(comment).getUserId();

        // then:
        assertFalse(authorizer.canDelete(root, commentId));
    }

    @Test
    public void testCanDelete_user_commentNotFound() {
        // given:
        doReturn(false).when(root).hasAuthority("ADMIN");
        doReturn(true).when(root).hasAuthority("USER");
        doThrow(new CommentNotFoundException(commentId)).when(commentService).findById(commentId);

        // then:
        assertThrows(CommentNotFoundException.class, () -> authorizer.canDelete(root, commentId));
    }
}
