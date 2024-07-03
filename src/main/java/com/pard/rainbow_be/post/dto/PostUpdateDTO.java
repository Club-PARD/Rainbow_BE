//package com.pard.rainbow_be.post.dto;
//
//import com.pard.rainbow_be.post.entity.Post;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class PostUpdateDTO {
//    private Long pid;
//    private Long uid;
//    private String title;
//    private String pictureLink;
//    private String content;
//    private LocalDateTime createdTime;
//    private LocalDateTime modifiedTime;
//
//    public Post toDTO(){
//        return Post.builder()
//                .pid(this.getPid())
//                .uid(this.getUid())
//                .title(this.getTitle())
//                .pictureLink((this.getPictureLink()))
//                .content(this.getContent())
//                .createdTime((this.getCreatedTime()))
//                .modifiedTime((this.getCreatedTime()))
//                .build();
//    }
//}
