package com.pard.rainbow_be.post.controller;


import com.pard.rainbow_be.exception.dto.CustomException;
import com.pard.rainbow_be.exception.dto.ErrorCode;
import com.pard.rainbow_be.exception.dto.ErrorResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createPost(@RequestBody PostCreateDTO postCreateDTO, @PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD post post wow!");
        try {
            postService.createPost(postCreateDTO, ownerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post/find/{ownerId}")
    @Operation(summary = "해당 유저의 게시물 전체 보기", description = "유저 아이디를 통해 해당 유저의 전체 게시물을 보게 만드는 메서드")
    public ResponseEntity<?> readAll(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD get user's all post");
        try {
            List<PostReadDTO> posts = postService.readAll(ownerId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post/find/{ownerId}/{postId}")
    @Operation(summary = "postId 별로 게시물 보기", description = "게시물을 보게 하는 메서드")
    public ResponseEntity<?> readPost(@PathVariable UUID ownerId, @PathVariable Long postId){
        log.info("\uD83D\uDCCD get user's checking post");
        try {
            PostReadDTO post = postService.findById(ownerId, postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post/count/{ownerId}")
    @Operation(summary = "userId 를 이용해서 해당 유저의 전체 게시물 수 리턴", description = "게시물 갯수 세기 메서드")
    public ResponseEntity<?> countByUserId(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD get post count");
        try {
            Integer countByUserId = postService.countByUserId(ownerId);
            return new ResponseEntity<>(countByUserId, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post/community")
    @Operation(summary = "Community 사진 보여주가", description = "해당 유저가 게시물을 만드는 메서드")
    public ResponseEntity<?> readsTheLatestPost(){
        log.info("\uD83D\uDCCD get public user's first posts");
        try{
            List<CommunityReadDto> communityReadDtos = postService.readsTheLatestPost();
            return new ResponseEntity<>(communityReadDtos, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/post/{postId}")
    @Operation(summary = "postId 를 이용해서 게시물 수정", description = "게시물을 수정 메서드")
    public ResponseEntity<?> updateByPid(@PathVariable Long postId, @RequestBody PostUpdateDTO postUpdateDTO){
        log.info("\uD83D\uDCCD patch post");
        try{
            postService.updateById(postId, postUpdateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "postId 를 이용해서 게시물 삭제", description = "게시물을 삭제 메서드")
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deleteById(@PathVariable Long postId){
        log.info("\uD83D\uDCCD delete post");
        try{
            postService.deleteById(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}