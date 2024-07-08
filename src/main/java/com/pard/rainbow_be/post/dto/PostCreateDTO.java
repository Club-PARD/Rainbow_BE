package com.pard.rainbow_be.post.dto;

import lombok.Getter;

@Getter
public class PostCreateDTO {
    private String postTitle;
    private String pictureUrl;
    private String postContent;
}
