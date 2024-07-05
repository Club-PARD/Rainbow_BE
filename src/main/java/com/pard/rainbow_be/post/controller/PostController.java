package com.pard.rainbow_be.post.controller;


import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
//import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    // Create
    @PostMapping("/{userId}")
    @Operation(summary = "게시물 등록", description = "해당 유저가 게시물을 만드는 메서드")
    public String createPost(@RequestBody PostCreateDTO postCreateDTO, @PathVariable UUID userId){
        postService.createPost(postCreateDTO, userId);
        return "Post Create Success";
    }

    // Read All
    @GetMapping("/find/{userId}")
    @Operation(summary = "유저의 게시물 보기, 해당 유제의 전체 게시물을 보게 만드는 메서드")
    public List<PostReadDTO> readAll(@PathVariable UUID userId){
        return postService.readAll(userId);
    }
    // Read By ID
    @GetMapping("/{postId}")
    @Operation(summary = "유저의 게시물 보기", description = "해당 유제의 전체 게시물을 보게 만드는 메서드")
    public PostReadDTO readPost(@PathVariable Long postId){
        return postService.findById(postId);
    }


    // Update
    @PatchMapping("/{postId}")
    @Operation(summary = "유저의 게시물 수정", description = "해당 유저의 게시물을 수정하게 만드는 메서드")
    public String updateByPid(@PathVariable Long postId, @RequestBody PostUpdateDTO postUpdateDTO){
        postService.updateById(postId, postUpdateDTO);
        return "Update Success";
    }

    // Delete
    @DeleteMapping("/{postId}")
    public String deleteById(@PathVariable Long postId){
        postService.deleteById(postId);
        return "Delete Success";
    }

}
