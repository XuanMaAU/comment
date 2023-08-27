package org.mmx.comment.service.impl;

import java.util.Optional;

import org.mmx.comment.dao.CommentRepository;
import org.mmx.comment.domain.Comment;
import org.mmx.comment.exception.AppException;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mmx.comment.exception.DatabaseException;
import org.mmx.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Comment Service implementation with database repository
 */
@Slf4j
@Transactional
@Service
public class CommentServiceImpl implements CommentService {
    /**
     * The database repository
     */
    private CommentRepository repo;

    @Autowired
    public CommentServiceImpl(CommentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Comment findById(long id) {
        log.debug("Find comment by id \"{}\"", id);

        try {
            Optional<Comment> comment = repo.findById(id);
            log.debug("Comment found: {}", comment);

            if (comment.isEmpty()) {
                throw new CommentNotFoundException(id);
            }

            return comment.get();
        } catch (AppException ae) {
            throw ae;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Comment editComment(long id, String comment) {
        log.debug("Edit comment by id \"{}\", new comment = {}", id, comment);

        try {
            // find the existing one
            Comment commentEntity = findById(id);
            log.debug("Existing comment is {}", commentEntity);

            commentEntity.setComment(comment);

            // save and flush
            return repo.saveAndFlush(commentEntity);
        } catch (AppException ae) {
            throw ae;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) {
        log.debug("Delete comment by id \"{}\"", id);

        try {
            // check existence
            if (!repo.existsById(id)) {
                log.debug("Comment with id \"{}\" doesn't exist", id);

                throw new CommentNotFoundException(id);
            }

            repo.deleteById(id);
        } catch (AppException ae) {
            throw ae;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
