package com.pard.rainbow_be.comment.entity;


import com.pard.rainbow_be.comment.dto.CommentCreateDTO;
import com.pard.rainbow_be.comment.dto.CommentUpdateDTO;
import com.pard.rainbow_be.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    public void update(CommentUpdateDTO dto){
        this.userComment = dto.getUserComment();
    }

    public static Comment toEntity(CommentCreateDTO commentCreateDTO, User owner, User writer){
        return Comment.builder()
            .userComment(commentCreateDTO.getUserComment())
            .owner(owner)
            .writer(writer)
            .build();
    }

}
