package com.pard.rainbow_be.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.post.entity.Post;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostReadDTO extends Post {
    private Long postId;
    private String postTitle;
    private String pictureLink;
    private String postContent;

    public PostReadDTO(Post post){
        this.postId = post.getPostId();
        this.postTitle = post.getPostContent();
        this.pictureLink = post.getPictureLink();
        this.postContent = post.getPostContent();
    }

//    public PostReadDTO(String postTitle, String postContent, String pictureLink,
//                       LocalDateTime createdTime, LocalDateTime modifiedTime) {
//        this.postTitle = postTitle;
//        this.postContent = postContent;
//        this.pictureLink = pictureLink;
//        this.createdTime = createdTime;
//        this.modifiedTime = modifiedTime;
//    }


//    public Post toEntity(){
//        return Post.builder()
//                .pid(this.getPid())
//                .title(this.getTitle())
//                .pictureLink(this.getPictureLink())
//                .content(this.getContent())
//                .createdTime(this.getCreatedTime())
//                .build();
//    }

}
