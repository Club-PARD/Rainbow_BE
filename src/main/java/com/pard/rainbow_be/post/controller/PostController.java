package com.pard.rainbow_be.post.controller;


import com.pard.rainbow_be.post.dto.CommunityReadDto;
import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.entity.Post;
import com.pard.rainbow_be.post.service.PostService;
import com.pard.rainbow_be.userToQuestion.service.UserQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @PostMapping("/post/{ownerId}")
    @Operation(summary = "게시물 등록", description = "해당 유저가 게시물을 만드는 메서드")
    public String createPost(@RequestBody PostCreateDTO postCreateDTO, @PathVariable UUID ownerId){
        postService.createPost(postCreateDTO, ownerId);
        log.info("📍make Post");
        return "Post Create Success";
    }

    @GetMapping("/post/find/{ownerId}")
    @Operation(summary = "해당 유저의 게시물 전체 보기", description = "유저 아이디를 통해 해당 유저의 전체 게시물을 보게 만드는 메서드")
    public List<PostReadDTO> readAll(@PathVariable UUID ownerId){
        log.info("📍view all Post for User");
        return postService.readAll(ownerId);
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "postId 별로 게시물 보기", description = "게시물을 보게 하는 메서드")
    public PostReadDTO readPost(@PathVariable Long postId){
        log.info("📍view Post");
        return postService.findById(postId);
    }

    @GetMapping("/post/count/{ownerId}")
    @Operation(summary = "userId 를 이용해서 해당 유저의 전체 게시물 수 리턴", description = "게시물 갯수 세기 메서드")
    public Integer countByUserId(@PathVariable UUID ownerId){
        log.info("📍 count Posts ");
        return postService.countByUserId(ownerId);
    }

    @GetMapping("/post/community")
    @Operation(summary = "Community 사진 보여주가", description = "해당 유저가 게시물을 만드는 메서드")
    public List<CommunityReadDto> readsTheLatestPost(){
        log.info("📍 커뮤니티 란 나왔당");
        return postService.readsTheLatestPost();
    }

    @PatchMapping("/post/{postId}")
    @Operation(summary = "postId 를 이용해서 게시물 수정", description = "게시물을 수정 메서드")
    public String updateByPid(@PathVariable Long postId, @RequestBody PostUpdateDTO postUpdateDTO){
        postService.updateById(postId, postUpdateDTO);
        log.info("📍update Post");
        return "Update Success";
    }

    @Operation(summary = "postId 를 이용해서 게시물 삭제", description = "게시물을 삭제 메서드")
    @DeleteMapping("/post/{postId}")
    public String deleteById(@PathVariable Long postId){
        postService.deleteById(postId);
        log.info("📍delete Post");
        return "Delete Success";
    }

}
