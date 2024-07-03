package com.pard.rainbow_be.post.dto;

import com.pard.rainbow_be.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter //probably shouldn't use setter
public class PostCreateDTO {
    private String postTitle;
//    private Long uid;
    private String pictureLink;
    private String postContent;
//    private LocalDateTime createdTime;
//    private LocalDateTime modifiedTime;

//    public Post toEntity(){
//        return Post.builder()
//                .postTitle(this.getPostTitle())
////                .uid(this.getUid())
//                .pictureLink(this.getPictureLink())
//                .postContent(this.getPostContent())
//                .createdTime(this.getCreatedTime())
//                .modifiedTime(this.getModifiedTime())
//                .build();
//
//    }

}
