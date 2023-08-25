package org.mmx.comment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mmx.comment.domain.Comment;
import org.mmx.comment.exception.CommentNotFoundException;
import org.mmx.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration Test for CommentController
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentService service;

    private long commentId = 5l;
    private long authorId = 101l;
    private String oldContent = "This is the fifth comment.";
    private Comment oldComment = new Comment(Long.valueOf(commentId), Long.valueOf(authorId), oldContent);
    private String newContent = "this is the new comment";
    private Comment expComment = new Comment(Long.valueOf(commentId), Long.valueOf(authorId), newContent);

    private long deleteCommentId = 2l;
    private Comment deleteComment = new Comment(Long.valueOf(deleteCommentId), Long.valueOf(51l), "This is the second comment.");

    private long nonExistingCommentId = 20l;

    @Test
    public void testEdit_success() throws Exception {
        // given:
        assertEquals(oldComment, service.findById(commentId));

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(commentId))
            .andExpect(jsonPath("$.authorId").value(authorId))
            .andExpect(jsonPath("$.comment").value(newContent))
            ;

        // then:
        assertEquals(expComment, service.findById(commentId));
    }

    @Test
    public void testEdit_notFound() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", nonExistingCommentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.commentId").value(nonExistingCommentId))
            ;
    }

	@Test
    public void testDelete_success() throws Exception {
        // given:
        assertEquals(deleteComment, service.findById(deleteCommentId));

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", deleteCommentId)
                        .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent())
            ;

        // then:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(deleteCommentId); });
    }

    @Test
    public void testDelete_notFound() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", nonExistingCommentId)
                        .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.commentId").value(nonExistingCommentId))
            ;
    }
}
