package org.mmx.comment.dao;

import org.mmx.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Database repository for comments
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Update the comment content
     */
    @Modifying
    @Query("UPDATE Comment SET comment = ?2 WHERE id = ?1")
    public void editComment(long id, String comment);
}
