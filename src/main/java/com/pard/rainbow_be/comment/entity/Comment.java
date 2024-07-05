package com.pard.rainbow_be.comment.entity;


import com.pard.rainbow_be.comment.dto.CommentCreateDTO;
import com.pard.rainbow_be.comment.dto.CommentUpdateDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String userComment;

    //private UUID userId; // hmm...

    public void update(CommentUpdateDTO dto){
        this.userComment = dto.getUserComment();
    }

    public static Comment toEntity(CommentCreateDTO commentCreateDTO){
        return Comment.builder()
            .userComment(commentCreateDTO.getUserComment())
            .build();
    }

}
