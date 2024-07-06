package com.pard.rainbow_be.post.dto;

import lombok.Getter;

@Getter
public class PostCreateDTO {
    private String postTitle;
//    private Long uid;
    private String pictureLink;
    private String postContent;


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
