package org.mmx.comment.security.authorization;

import org.mmx.comment.domain.Comment;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mmx.comment.security.AppUserDetails;
import org.mmx.comment.security.SecurityRole;
import org.mmx.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * The application authorizer to check canEdit and canDelete
 */
@Slf4j
@Component("authz")
public class AppAuthorizer {
    private CommentService commentService;

    @Autowired
    public AppAuthorizer(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Whether the current user can edit the comment with the specified id or not.
     * A user can only edit the comment if he / she is an admin or is the author of the comment.
     *
     * @param root the method security expression
     * @param commentId the comment id
     *
     * @return true if the current user can edit the comment, false otherwise
     * @throws CommentNotFoundException, results in 404
     */
    public boolean canEdit(MethodSecurityExpressionOperations root, long commentId) {
        log.debug("canEdit(root, commentId): root = {}, commentId = {}", root, commentId);

        Authentication auth = root.getAuthentication();

        // current user is an admin
        if (root.hasAuthority(SecurityRole.ADMIN.name())) {
            log.debug("Current user is an admin");
            return true;
        }

        // not a user
        if (!root.hasAuthority(SecurityRole.USER.name())) {
            log.debug("Current user is not a user");
            return false;
        }

        AppUserDetails user = (AppUserDetails)auth.getPrincipal();

        // comment not found, the exception is thrown and the status will be 404 Not Found
        Comment comment = commentService.findById(commentId);

        log.debug("Current user = {}, comment = {}", user, comment);

        // the user is owner of the comment
        return user.getUser().getId() == comment.getUserId();
    }

    /**
     * Whether the current user can delete the comment with the specified id or not.
     * A user can only delete the comment if he / she is an admin or is the author of the comment.
     *
     * @param root the method security expression
     * @param commentId the comment id
     *
     * @return true if the current user can delete the comment, false otherwise
     * @throws CommentNotFoundException, results in 404
     */
    public boolean canDelete(MethodSecurityExpressionOperations root, long commentId) {
        log.debug("canDelete(root, commentId): root = {}, commentId = {}", root, commentId);

        Authentication auth = root.getAuthentication();

        // current user is an admin
        if (root.hasAuthority(SecurityRole.ADMIN.name())) {
            log.debug("Current user is an admin");
            return true;
        }

        // not a user
        if (!root.hasAuthority(SecurityRole.USER.name())) {
            log.debug("Current user is not a user");
            return false;
        }

        AppUserDetails user = (AppUserDetails)auth.getPrincipal();

        // comment not found, the exception is thrown and the status will be 404 Not Found
        Comment comment = commentService.findById(commentId);

        log.debug("Current user = {}, comment = {}", user, comment);

        // the user is owner of the comment
        return user.getUser().getId() == comment.getUserId();
    }
}
