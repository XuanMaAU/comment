package org.mmx.comment.controller;

import org.mmx.comment.domain.Comment;
import org.mmx.comment.dto.CommentDto;
import org.mmx.comment.service.CommentService;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * The RESTful API controller for the comments resource
 */
@RestController
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentController {
    /**
     * The comment service
     */
    private CommentService commentService;
    /**
     * The model mapper between domain object and the dto
     */
    private ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService,
                             @Qualifier("commentsModelMapper") ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    /**
     * Update the content of the comment with the specified id
     *
     * @param id the comment id
     * @param content the comment content to update
     *
     * @return the update comment object
     */
    @PreAuthorize("@authz.canEdit(#root, #id)")
    @PostMapping("/{id}/editComment")
    public ResponseEntity<CommentDto> edit(@PathVariable long id,
                                           @RequestBody(required = false) String content) {
        log.debug("Edit comment: id = {}, content = {}", id, content);

        // make it blank instead of null
        if (content == null) {
            content = "";
        }

        // edit the comment
        Comment comment = commentService.editComment(id, content);

        CommentDto dto = toDto(comment);

        log.debug("Edit comment: id = {}, dto = {}", id, dto);

        return ResponseEntity.ok(dto);
    }

    /**
     * Delete the comment with the specified id
     */
    @PreAuthorize("@authz.canDelete(#root, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        log.debug("Delete comment: id = {}", id);

        // delete the comment object
        commentService.delete(id);

        log.debug("Delete comment: id = {} deleted", id);

        // 204 is returned if the comment is deleted successfully
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Convert the domain object to dto
     *
     * @param comment the domain object
     *
     * @return the converted dto
     */
    private CommentDto toDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    /**
     * Convert dto to the domain object
     *
     * @param commentDto the dto
     *
     * @return the converted dto
     */
    private Comment toEntity(CommentDto dto) {
        return modelMapper.map(dto, Comment.class);
    }
}
