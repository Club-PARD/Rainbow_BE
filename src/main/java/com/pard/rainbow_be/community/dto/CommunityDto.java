package com.pard.rainbow_be.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.post.dto.PostReadDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommunityDto {
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Read{
        private UUID userId;
        private String pictureLink;
        private LocalDateTime createdTime;

        public Read(PostReadDTO post) {
            this.userId = post.getUserid();
            this.pictureLink = post.getPictureLink();
            this.createdTime = post.getCreatedTime();
        }
    }
}
