package com.pard.rainbow_be.comment.controller;

import com.pard.rainbow_be.comment.dto.CommentCreateDTO;
import com.pard.rainbow_be.comment.dto.CommentReadDTO;
import com.pard.rainbow_be.comment.dto.CommentUpdateDTO;
import com.pard.rainbow_be.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{ownerId}/{writerId}")
    @Operation(summary = "댓글 등록", description = "공간의 주인 ID (ownerId)를 통해 댓글을 쓸 공간을 지정하고, 작성자 ID(writerId)를 통해 해당 공간에 댓글을 작성합니다")
    public String createComment(@RequestBody CommentCreateDTO commentCreateDTO, @PathVariable UUID ownerId, @PathVariable UUID writerId){
        log.info("\uD83D\uDCCD post comment into owner user");
        commentService.createComment(commentCreateDTO, ownerId, writerId);
        return "Comment Create Success";
    }

    @GetMapping("/readAll/{ownerId}")
    @Operation(summary = "댓글 전체 보기", description = "공간 주인의 ID(ownerId)를 지정하여 해당 공간에 작성된 모든 댓글을 불러옵니다")
    public List<CommentReadDTO>readAll(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD read all comment of owner user");
        return commentService.readAll(ownerId);
    }

    @GetMapping("/readOne/{ownerId}/{commentId}")
    @Operation(summary = "댓글 하나만 보기 (테스트용)", description = "공간 주인의 ID(ownerId) 그리고 댓글의 ID(commentId)를 지정하여 한개의 댓글을 불러옵니다")
    public Optional<CommentReadDTO> readOneComment(@PathVariable UUID ownerId, @PathVariable Long commentId){
        return commentService.readOne(ownerId, commentId);
    }

    @GetMapping("/count/{ownerId}")
    @Operation(summary = "owner의 아이디 갯수 세기", description = "공간 주인의 ID(ownerId)를 지정하여 해당 댓글의 갯수를 불러오는 방법")
    public Integer countCommentOwner(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD Comment Count");
        return commentService.countOwnerComment(ownerId);
    }


    @PatchMapping("/{writerId}/{commentId}")
    @Operation(summary = "댓글 수정하기", description = "댓글 작성자의 ID(writerId) 그리고 댓글의 ID(commentId)를 지정하여 댓글을 수정합니다")
    public String updateByWriterId(@PathVariable UUID writerId,@PathVariable Long commentId, @RequestBody CommentUpdateDTO dto){
        log.info("\uD83D\uDCCD update comment");
        commentService.updateByWriterIdAndCommentId(writerId, commentId, dto);
        return "Update Success";
    }


    @DeleteMapping("/deleteByWriterId/{writerId}/{commentId}")
    @Operation(summary = "댓글 삭제하기(작성자가 삭제)", description = "댓글 작성자의 ID(writerId) 그리고 댓글의 ID(commentId)를 지정하여 댓글을 삭제합니다")
    public Boolean deleteCommentByWriterId(@PathVariable UUID writerId, @PathVariable Long commentId){
        log.info("\uD83D\uDCCD Owner deletes comment");
        return commentService.deleteCommentByWriterIdAndCommentId(writerId, commentId);
    }

    @DeleteMapping("/deleteByOwnerId/{ownerId}/{commentId}")
    @Operation(summary = "댓글 삭제하기(주인이 삭제)", description = "공간 주인의 ID(ownerId) 그리고 댓글의 ID(commentId)를 지정하여 댓글을 삭제합니다")
    public Boolean deleteCommentByOwnerId(@PathVariable UUID ownerId, @PathVariable Long commentId){
        log.info("\uD83D\uDCCD Writer deletes comment");
        return commentService.deleteCommentByOwnerIdAndCommentId(ownerId, commentId);
    }
}
