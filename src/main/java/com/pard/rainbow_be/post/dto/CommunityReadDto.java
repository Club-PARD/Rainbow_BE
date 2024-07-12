package com.pard.rainbow_be.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.post.entity.Post;
import lombok.Getter;

import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityReadDto{

    private final UUID userId;
    private final Long postId;
    private final String postTitle;
    private final String pictureUrl;


    public CommunityReadDto(Post post) {
        this.userId = post.getUser().getId();
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.pictureUrl = post.getPictureUrl();
    }
}
