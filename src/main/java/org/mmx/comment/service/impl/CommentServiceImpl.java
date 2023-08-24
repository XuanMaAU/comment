package org.mmx.comment.service.impl;

import java.util.Optional;

import org.mmx.comment.dao.CommentRepository;
import org.mmx.comment.domain.Comment;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mmx.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * Comment Service implementation with database repository
 */
@Service
@Transactional
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
        Optional<Comment> comment = repo.findById(id);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException(id);
        }

        return comment.get();
    }

    @Override
    public void editComment(long id, String comment) {
        // check existence
        if (!repo.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        repo.editComment(id, comment);
    }

    @Override
    public void delete(long id) {
        // check existence
        if (!repo.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        repo.deleteById(id);
    }
}
