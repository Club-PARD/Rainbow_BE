package com.pard.rainbow_be.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.post.entity.Post;
import lombok.*;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostReadDTO extends Post {
    private Long postId;
    private String postTitle;
    private String pictureUrl;
    private String postContent;

    public PostReadDTO(Post post){
        this.postId = post.getPostId();
        this.postTitle = post.getPostContent();
        this.pictureUrl = post.getPictureUrl();
        this.postContent = post.getPostContent();
    }
}
