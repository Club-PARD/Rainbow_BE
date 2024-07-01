package com.pard.rainbow_be.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pid;
    private Long uid;
    private String title;
    private String pictureLink;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

//    public PostReadDTO toDTO(){
//        return PostReadDTO.builder()
//    }

}
