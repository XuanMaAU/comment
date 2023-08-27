package org.mmx.comment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mmx.comment.AppTestBase;
import org.mmx.comment.dao.CommentRepository;
import org.mmx.comment.domain.Comment;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Unit test cases for CommentServiceImpl
 */
public class CommentServiceImplTest extends AppTestBase {
    @Mock
    private CommentRepository repo;

    @InjectMocks
    private CommentServiceImpl service;

    private long commentId = 5l;
    private Comment expComment = new Comment(Long.valueOf(commentId), Long.valueOf(2l), "this is a test comment");
    private String newContent = "this is the new comment";

    @Test
    public void testFindById_positive() {
        // given:
        doReturn(Optional.of(expComment)).when(repo).findById(commentId);

        // when:
        Comment resComment = service.findById(commentId);

        // then:
        assertEquals(expComment, resComment);
    }

    @Test
    public void testFindById_notFound() {
        // given:
        doReturn(Optional.empty()).when(repo).findById(commentId);

        // when:
        testNotFoundException(() -> service.findById(commentId));
    }

    @Test
    public void testEditComment_positive() {
        // given:
        doReturn(Optional.of(expComment)).when(repo).findById(commentId);

        // when:
        service.editComment(commentId, newContent);

        // then:
        // the repo.editComment() method is called with the specified parameters
        verify(repo).saveAndFlush(eq(expComment));
    }

    @Test
    public void testEditComment_notFound() {
        // given:
        doReturn(Optional.empty()).when(repo).findById(commentId);

        // when:
        testNotFoundException(() -> service.editComment(commentId, newContent));
    }

    @Test
    public void testDelete_positive() {
        // given:
        doReturn(true).when(repo).existsById(commentId);

        // when:
        service.delete(commentId);

        // then:
        // the repo.editComment() method is called with the specified parameters
        verify(repo).deleteById(eq(commentId));
    }

    @Test
    public void testDelete_notFound() {
        // given:
        doReturn(false).when(repo).existsById(commentId);

        // when:
        testNotFoundException(() -> service.delete(commentId));
    }

    private void testNotFoundException(Executable method) {
        CommentNotFoundException ex = assertThrows(CommentNotFoundException.class, method);
        assertEquals(expComment.getId(), ex.getId());
    }
}
