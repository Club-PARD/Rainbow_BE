package com.pard.rainbow_be.post.controller;


import com.pard.rainbow_be.post.dto.CommunityReadDto;
import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.service.PostService;
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
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    // Create
    @PostMapping("/{userId}")
    @Operation(summary = "게시물 등록", description = "해당 유저가 게시물을 만드는 메서드")
    public String createPost(@RequestBody PostCreateDTO postCreateDTO, @PathVariable UUID userId){
        postService.createPost(postCreateDTO, userId);
        log.info("📍make Post");
        return "Post Create Success";
    }

    // Read All in Descending Order
    @GetMapping("/find/{userId}")
    @Operation(summary = "해당 유저의 게시물 전체 보기", description = "유저 아이디를 통해 해당 유저의 전체 게시물을 보게 만드는 메서드")
    public List<PostReadDTO> readAll(@PathVariable UUID userId){
        log.info("📍view all Post for User");
        return postService.readAll(userId);
    }

    // Read Most Recent Post per User // (test 용 controller)
//    @GetMapping("/recent/{userId}")
//    @Operation(summary = "해당 user 의 가장 최근 게시물 보기", description = " 해당 유저의 가장 최근 게시물을 보게 만드는 메서드")
//    public Optional readMostRecent(@PathVariable UUID userId){
//        log.info("📍view the Most Recent Post for the designated User");
//        return postService.readFirst(userId);
//    }

    // Read By ID
    @GetMapping("/{postId}")
    @Operation(summary = "postId 별로 게시물 보기", description = "게시물을 보게 하는 메서드")
    public PostReadDTO readPost(@PathVariable Long postId){
        log.info("📍view Post");
        return postService.findById(postId);
    }

    @GetMapping("/count/{userId}")
    @Operation(summary = "userId 를 이용해서 해당 유저의 전체 게시물 수 리턴", description = "게시물 갯수 세기 메서드")
    public Integer countByUserId(@PathVariable UUID userId){
        log.info("📍 count Posts ");
        return postService.countByUserId(userId);
    }

    @GetMapping("/community")
    @Operation(summary = "Community 사진 보여주가", description = "해당 유저가 게시물을 만드는 메서드")
    public List<CommunityReadDto> readsTheLatestPost(){
        log.info("📍 커뮤니티 란 나왔당");
        return postService.readsTheLatestPost();
    }

    // Update
    @PatchMapping("/{postId}")
    @Operation(summary = "postId 를 이용해서 게시물 수정", description = "게시물을 수정 메서드")
    public String updateByPid(@PathVariable Long postId, @RequestBody PostUpdateDTO postUpdateDTO){
        postService.updateById(postId, postUpdateDTO);
        log.info("📍update Post");
        return "Update Success";
    }

    // Delete
    @Operation(summary = "postId 를 이용해서 게시물 삭제", description = "게시물을 삭제 메서드")
    @DeleteMapping("/{postId}")
    public String deleteById(@PathVariable Long postId){
        postService.deleteById(postId);
        log.info("📍delete Post");
        return "Delete Success";
    }

}
