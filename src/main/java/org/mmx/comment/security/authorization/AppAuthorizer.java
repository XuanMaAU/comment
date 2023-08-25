package org.mmx.comment.security.authorization;

import org.mmx.comment.domain.Comment;
import org.mmx.comment.security.AppUserDetails;
import org.mmx.comment.security.SecurityRole;
import org.mmx.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * The application authorizer for canEdit and canDelete
 */
@Slf4j
@Component("authz")
public class AppAuthorizer {
    private CommentService commentService;

    @Autowired
    public AppAuthorizer(CommentService commentService) {
        this.commentService = commentService;
    }

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

        // comment not found?
        Comment comment = commentService.findById(commentId);

        log.debug("Current user = {}, comment = {}", user, comment);

        // the user is owner of the comment
        return user.getUser().getId() == comment.getUserId();
    }

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

        Comment comment = commentService.findById(commentId);

        log.debug("Current user = {}, comment = {}", user, comment);

        // the user is owner of the comment
        return user.getUser().getId() == comment.getUserId();
    }
}
