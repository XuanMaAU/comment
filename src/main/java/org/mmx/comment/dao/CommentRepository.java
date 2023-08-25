package org.mmx.comment.dao;

import org.mmx.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Database repository for comments
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
