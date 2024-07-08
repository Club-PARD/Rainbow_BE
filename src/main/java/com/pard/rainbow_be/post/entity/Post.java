package com.pard.rainbow_be.post.entity;

import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String postTitle;

    @Column(columnDefinition = "TEXT")
    private String pictureUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;

    public void update(PostUpdateDTO dto){
        this.pictureUrl = dto.getPictureUrl();
        this.postContent = dto.getPostContent();
    }

    public static Post toEntity(PostCreateDTO postCreateDTO, User user){
        return Post.builder()
                .postTitle(postCreateDTO.getPostTitle())
                .pictureUrl(postCreateDTO.getPictureLink())
                .postContent(postCreateDTO.getPostContent())
                .user(user)
                .build();
    }

}
