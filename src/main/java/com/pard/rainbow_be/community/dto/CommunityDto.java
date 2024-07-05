package com.pard.rainbow_be.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class CommunityDto {
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Read{
        private UUID userId;
        private String pictureLink;
        private String postTitle;

        public Read(Post post) {
            this.userId = post.getUser().getId();
            this.pictureLink = post.getPictureLink();
            this.postTitle = post.getPostTitle();
        }
    }
}
