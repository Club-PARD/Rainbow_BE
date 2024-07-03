package com.pard.rainbow_be.post.entity;

import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    @CreationTimestamp()
    @Column(updatable = false)
    private LocalDateTime modifiedTime;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    //IDK what this method is for
//    public void setUserWithIdOne(User user){
//        this.user = user;
//    }

    public static Post toEntity(PostCreateDTO postCreateDTO){
        return Post.builder()
                .postTitle(postCreateDTO.getPostTitle())
                .pictureLink(postCreateDTO.getPictureLink())
                .postContent(postCreateDTO.getPostContent())
//                .user(new UserDto.Read(userId))
                .build();
    }

//    public static Post toEntity(String postTitle, String pictureLink, String postContent
//                                ){
//        return Post.builder()
//                .post
//    }



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
