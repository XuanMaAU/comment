package org.mmx.comment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Comment DTO
 */
@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private Long authorId;
    private String comment;
}
