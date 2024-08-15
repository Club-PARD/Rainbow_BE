package com.pard.rainbow_be.comment.controller;

import com.pard.rainbow_be.comment.dto.CommentCreateDTO;
import com.pard.rainbow_be.comment.dto.CommentReadDTO;
import com.pard.rainbow_be.comment.dto.CommentUpdateDTO;
import com.pard.rainbow_be.comment.service.CommentService;
import com.pard.rainbow_be.exception.dto.CustomException;
import com.pard.rainbow_be.exception.dto.ErrorCode;
import com.pard.rainbow_be.exception.dto.ErrorResponse;
import com.pard.rainbow_be.post.dto.CommunityReadDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{ownerId}/{writerId}")
    @Operation(summary = "댓글 등록", description = "공간의 주인 ID (ownerId)를 통해 댓글을 쓸 공간을 지정하고, 작성자 ID(writerId)를 통해 해당 공간에 댓글을 작성합니다")
    public ResponseEntity<?> createComment(@RequestBody CommentCreateDTO commentCreateDTO, @PathVariable UUID ownerId, @PathVariable UUID writerId){
        log.info("\uD83D\uDCCD post comment into owner user");
        try{
            commentService.createComment(commentCreateDTO, ownerId, writerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/comment/readAll/{ownerId}")
    @Operation(summary = "댓글 전체 보기", description = "공간 주인의 ID(ownerId)를 지정하여 해당 공간에 작성된 모든 댓글을 불러옵니다")
    public ResponseEntity<?> readAll(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD read all comment of owner user");
        try{
            List<CommentReadDTO> comments = commentService.readAll(ownerId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/comment/readOne/{ownerId}/{commentId}")
    @Operation(summary = "댓글 하나만 보기 (테스트용)", description = "공간 주인의 ID(ownerId) 그리고 댓글의 ID(commentId)를 지정하여 한개의 댓글을 불러옵니다")
    public Optional<CommentReadDTO> readOneComment(@PathVariable UUID ownerId, @PathVariable Long commentId){
        return commentService.readOne(ownerId, commentId);
    }

    @GetMapping("/comment/count/{ownerId}")
    @Operation(summary = "owner의 아이디 갯수 세기", description = "공간 주인의 ID(ownerId)를 지정하여 해당 댓글의 갯수를 불러오는 방법")
    public ResponseEntity<?> countCommentOwner(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD Comment Count");
        try{
            Integer count = commentService.countOwnerComment(ownerId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/comment/{writerId}/{commentId}")
    @Operation(summary = "댓글 수정하기", description = "댓글 작성자의 ID(writerId) 그리고 댓글의 ID(commentId)를 지정하여 댓글을 수정합니다")
    public ResponseEntity updateByWriterId(@PathVariable UUID writerId,@PathVariable Long commentId, @RequestBody CommentUpdateDTO dto){
        log.info("\uD83D\uDCCD update comment");
        try{
            commentService.updateByWriterIdAndCommentId(writerId, commentId, dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/comment/deleteByWriterId/{writerId}/{commentId}")
    @Operation(summary = "댓글 삭제하기(작성자가 삭제)", description = "댓글 작성자의 ID(writerId) 그리고 댓글의 ID(commentId)를 지정하여 댓글을 삭제합니다")
    public ResponseEntity<?> deleteCommentByWriterId(@PathVariable UUID writerId, @PathVariable Long commentId){
        log.info("\uD83D\uDCCD Owner deletes comment");
        try{
            boolean check = commentService.deleteCommentByWriterIdAndCommentId(writerId, commentId);
            return new ResponseEntity<>(check, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/comment/deleteByOwnerId/{ownerId}/{commentId}")
    @Operation(summary = "댓글 삭제하기(주인이 삭제)", description = "공간 주인의 ID(ownerId) 그리고 댓글의 ID(commentId)를 지정하여 댓글을 삭제합니다")
    public ResponseEntity<?> deleteCommentByOwnerId(@PathVariable UUID ownerId, @PathVariable Long commentId){
        log.info("\uD83D\uDCCD Writer deletes comment");
        try{
            boolean check = commentService.deleteCommentByOwnerIdAndCommentId(ownerId, commentId);
            return new ResponseEntity<>(check, HttpStatus.OK);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
