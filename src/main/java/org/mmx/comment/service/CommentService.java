package org.mmx.comment.service;

import org.mmx.comment.domain.Comment;

/**
 * Comment Service to manipulate comments
 */
public interface CommentService {
    /**
     * Get the comment object by id
     *
     * @param id the id for the comment
     *
     * @return the comment object with the specified id
     *
     * @throws CommentNotFoundException if the comment with the id doesn't exist in the database
     */
    Comment findById(long id);

    /**
     * Edit the comment
     *
     * @param id the comment id
     * @param comment the new comment
     *
     * @throws CommentNotFoundException if the comment with the id doesn't exist in the database
     */
    void editComment(long id, String comment);

    /**
     * Delete a comment
     *
     * @throws CommentNotFoundException if the comment with the id doesn't exist in the database
     */
    void delete(long id);
}
