package com.pard.rainbow_be.post.entity;

import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.user.entity.User;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Lob
    @Column(name ="picture_url", nullable = false, columnDefinition = "TEXT")
    private String pictureUrl;

    @Lob
    @Column(name="post_content",nullable = false, columnDefinition = "TEXT")
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_time",nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    public void updateAll(PostUpdateDTO dto){
        this.pictureUrl = dto.getPictureUrl();
        this.postContent = dto.getPostContent();
    }

    public void updateContent(PostUpdateDTO dto){
        this.postContent = dto.getPostContent();
    }

    public static Post toEntity(PostCreateDTO postCreateDTO, User user){
        return Post.builder()
                .postTitle(postCreateDTO.getPostTitle())
                .pictureUrl(postCreateDTO.getPictureUrl())
                .postContent(postCreateDTO.getPostContent())
                .user(user)
                .build();
    }

}
