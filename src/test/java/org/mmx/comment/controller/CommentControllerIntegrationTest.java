package org.mmx.comment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private long userId = 101l;
    private String oldContent = "This is the fifth comment.";
    private Comment oldComment = new Comment(Long.valueOf(commentId), Long.valueOf(userId), oldContent);
    private String newContent = "this is the new comment";
    private Comment expComment = new Comment(Long.valueOf(commentId), Long.valueOf(userId), newContent);

    private long deleteCommentId = 2l;
    private Comment deleteComment = new Comment(Long.valueOf(deleteCommentId), Long.valueOf(51l), "This is the second comment.");

    private long adminCommentId = 1l;
    private long adminUserId = 51l;
    private String adminOldContent = "This is the first comment.";
    private Comment adminOldComment = new Comment(Long.valueOf(adminCommentId), Long.valueOf(adminUserId), adminOldContent);
    private Comment adminExpComment = new Comment(Long.valueOf(adminCommentId), Long.valueOf(adminUserId), newContent);

    private long adminDeleteCommentId = 4l;
    private Comment adminDeleteComment = new Comment(Long.valueOf(adminDeleteCommentId), Long.valueOf(101l), "This is the fourth comment.");

    private long unauthorizedCommentId = 6l;
    private Comment unauthorizedComment = new Comment(Long.valueOf(unauthorizedCommentId), Long.valueOf(101l), "This is the sixth comment.");

    private long nonExistingCommentId = 20l;

    @Test
    public void testAnonymous() throws Exception {
        // when:
        mockMvc.perform(post("/api/v1/test", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;
    }

    @Test
    public void testWrongUrl() throws Exception {
        // when:
        mockMvc.perform(post("/api/v1/test", commentId)
                        .with(httpBasic("user1", "user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isNotFound())
            ;
    }

    /* ======== Edit Test cases ======== */

    @Test
    public void testEdit_wrongPassword() throws Exception {
        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", commentId)
                        .with(httpBasic("user2", "wrong password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;
    }

    @Test
    public void testEdit_invalidUser() throws Exception {
        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", commentId)
                        .with(httpBasic("nonExistingUser", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;
    }

    @Test
    public void testEdit_wrongMethod() throws Exception {
        // when:
        mockMvc.perform(put("/api/v1/comments/{id}/editComment", commentId)
                        .with(httpBasic("user2", "user2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            ;
    }

    @Test
    public void testEdit_invalidId() throws Exception {
        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", "testId")
                        .with(httpBasic("user1", "user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isBadRequest())
            ;
    }

    @Test
    public void testEdit_otherUser() throws Exception {
        // given:
        assertEquals(oldComment, service.findById(commentId));

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", commentId)
                        .with(httpBasic("user1", "user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isForbidden())
            ;

        // then:
        assertEquals(oldComment, service.findById(commentId));
    }

    @Test
    public void testEdit_user_success() throws Exception {
        // given:
        assertEquals(oldComment, service.findById(commentId));

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", commentId)
                        .with(httpBasic("user2", "user2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(commentId))
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.comment").value(newContent))
            ;

        // then:
        assertEquals(expComment, service.findById(commentId));
    }

    @Test
    public void testEdit_user_notFound() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", nonExistingCommentId)
                        .with(httpBasic("user2", "user2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.commentId").value(nonExistingCommentId))
            ;
    }

    @Test
    public void testEdit_admin_success() throws Exception {
        // given:
        assertEquals(adminOldComment, service.findById(adminCommentId));

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", adminCommentId)
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(adminCommentId))
            .andExpect(jsonPath("$.userId").value(adminUserId))
            .andExpect(jsonPath("$.comment").value(newContent))
            ;

        // then:
        assertEquals(adminExpComment, service.findById(adminCommentId));
    }

    @Test
    public void testEdit_admin_notFound() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", nonExistingCommentId)
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.commentId").value(nonExistingCommentId))
            ;
    }

    @Test
    public void testEdit_unauthorized() throws Exception {
        // given:
        assertEquals(unauthorizedComment, service.findById(unauthorizedCommentId));

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", unauthorizedCommentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;

        // then:
        assertEquals(unauthorizedComment, service.findById(unauthorizedCommentId));
    }

    @Test
    public void testEdit_notFound_unauthorized() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(post("/api/v1/comments/{id}/editComment", nonExistingCommentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;
    }

    /* ======== Delete Test cases ======== */

    @Test
    public void testDelete_invalidId() throws Exception {
        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", "testId")
                        .with(httpBasic("admin", "admin"))
                        )
            .andDo(print())
            .andExpect(status().isBadRequest())
            ;
    }

    @Test
    public void testDelete_wrongMethod() throws Exception {
        // when:
        mockMvc.perform(patch("/api/v1/comments/{id}", deleteCommentId)
                        .with(httpBasic("user1", "user1"))
                        )
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            ;
    }

    @Test
    public void testDelete_invalidUser() throws Exception {
        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", commentId)
                        .with(httpBasic("nonExistingUser", "password"))
                        )
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;
    }

    @Test
    public void testDelete_otherUser() throws Exception {
        // given:
        assertEquals(unauthorizedComment, service.findById(unauthorizedCommentId));

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", unauthorizedCommentId)
                        .with(httpBasic("user1", "user1"))
                        )
            .andDo(print())
            .andExpect(status().isForbidden())
            ;

        // then:
        assertEquals(unauthorizedComment, service.findById(unauthorizedCommentId));
    }

    @Test
    public void testDelete_user_success() throws Exception {
        // given:
        assertEquals(deleteComment, service.findById(deleteCommentId));

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", deleteCommentId)
                        .with(httpBasic("user1", "user1"))
                        )
            .andDo(print())
            .andExpect(status().isNoContent())
            ;

        // then:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(deleteCommentId); });
    }

    @Test
    public void testDelete_user_notFound() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", nonExistingCommentId)
                        .with(httpBasic("user2", "user2"))
                        )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.commentId").value(nonExistingCommentId))
            ;
    }

    @Test
    public void testDelete_admin_success() throws Exception {
        // given:
        assertEquals(adminDeleteComment, service.findById(adminDeleteCommentId));

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", adminDeleteCommentId)
                        .with(httpBasic("admin", "admin"))
                        )
            .andDo(print())
            .andExpect(status().isNoContent())
            ;

        // then:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(adminDeleteCommentId); });
    }

    @Test
    public void testDelete_admin_notFound() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", nonExistingCommentId)
                        .with(httpBasic("admin", "admin"))
                        )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.commentId").value(nonExistingCommentId))
            ;
    }

    @Test
    public void testDelete_unauthorized() throws Exception {
        // given:
        assertEquals(unauthorizedComment, service.findById(unauthorizedCommentId));

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", unauthorizedCommentId)
                        )
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;

        // then:
        assertEquals(unauthorizedComment, service.findById(unauthorizedCommentId));
    }

    @Test
    public void testDelete_notFound_unauthorized() throws Exception {
        // given:
        assertThrows(CommentNotFoundException.class, () -> { service.findById(nonExistingCommentId); });

        // when:
        mockMvc.perform(delete("/api/v1/comments/{id}", nonExistingCommentId)
                        )
            .andDo(print())
            .andExpect(status().isUnauthorized())
            ;
    }
}
