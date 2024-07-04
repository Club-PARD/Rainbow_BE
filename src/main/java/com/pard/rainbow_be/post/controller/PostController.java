package com.pard.rainbow_be.post.controller;


import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
//import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.service.PostService;
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
    @PostMapping("")
    public String createPost(@RequestBody PostCreateDTO postCreateDTO){
        postService.createPost(postCreateDTO);
        return "Post Create Success";
    }

    // Read All
    @GetMapping("")
    public List<PostReadDTO> readAll(){
        return postService.readAll();
    }
    // Read By ID
    @GetMapping("/{postId}")
    public PostReadDTO readPost(@PathVariable Long postId){
        return postService.findById(postId);
    }

    // Update
    @PatchMapping("/{postId}")
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
