package com.pard.rainbow_be.comment.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.comment.entity.Comment;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentReadDTO extends Comment {
    private Long commentId;
    private String userComment;

    public CommentReadDTO(Comment comment){
        this.commentId = comment.getCommentId();
        this.userComment = comment.getUserComment();
    }
}
