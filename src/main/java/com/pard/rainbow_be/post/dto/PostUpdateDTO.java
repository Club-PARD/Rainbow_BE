package com.pard.rainbow_be.post.dto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDTO {
    private String pictureUrl;
    private String postContent;
}
