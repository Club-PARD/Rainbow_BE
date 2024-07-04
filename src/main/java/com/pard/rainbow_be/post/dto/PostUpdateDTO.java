package com.pard.rainbow_be.post.dto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDTO {
    private String postTitle;
    private String pictureLink;
    private String postContent;
}
