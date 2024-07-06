package com.pard.rainbow_be.post.entity;

import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
//    private Long uid; // user who writes the post

    private String postTitle;
    private String pictureLink;
    private String postContent;
    @CreationTimestamp()
    @Column(updatable = false)
    private LocalDateTime createdTime;
    @UpdateTimestamp()
    @Column()
    private LocalDateTime modifiedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //IDK what this method is for
//    public void update(PostUpdateDTO dto){
//    public void update(PostCreateDTO dto){
    public void update(PostUpdateDTO dto){
        this.postTitle = dto.getPostTitle();
        this.pictureLink = dto.getPictureLink();
        this.postContent = dto.getPostContent();
    }

    public static Post toEntity(PostCreateDTO postCreateDTO, User user){
        return Post.builder()
                .postTitle(postCreateDTO.getPostTitle())
                .pictureLink(postCreateDTO.getPictureLink())
                .postContent(postCreateDTO.getPostContent())
                .user(user)
                .build();
    }


//    public PostReadDTO toDTO(){
//        return PostReadDTO.builder()
//                .pid(this.getPostId())
//                .title(this.getPostTitle())
//                .pictureLink(this.getPictureLink())
//                .content(this.getPostContent())
//                .createdTime(this.getCreatedTime())
//                .modifiedTime(this.getModifiedTime())
//                .build();
//    }

}
