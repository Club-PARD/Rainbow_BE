package com.pard.rainbow_be.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.post.entity.Post;
import com.pard.rainbow_be.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityReadDto{

    private final User user;
    private final String postTitle;
    private final String pictureLink;


    public CommunityReadDto(Post post) {
        this.user = post.getUser();
        this.postTitle = post.getPostTitle();
        this.pictureLink = post.getPictureLink();
    }
}
