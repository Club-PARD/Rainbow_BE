package com.pard.rainbow_be.comment.controller;

import com.pard.rainbow_be.comment.dto.CommentCreateDTO;
import com.pard.rainbow_be.comment.dto.CommentReadDTO;
import com.pard.rainbow_be.comment.dto.CommentUpdateDTO;
import com.pard.rainbow_be.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public String createComment(@RequestBody CommentCreateDTO commentCreateDTO){
        commentService.createComment(commentCreateDTO);
        return "Comment Create Success";
    }

    @GetMapping("")
    public List<CommentReadDTO>readAll(){
        return commentService.readAll();
    }

    @GetMapping("/{commentId}")
    public CommentReadDTO readComment(@PathVariable Long commentId){
        return commentService.findById(commentId);
    }

    @PatchMapping("/{commentId}")
    public String updateById(@PathVariable Long commentId, @RequestBody CommentUpdateDTO dto){
        commentService.updateById(commentId, dto);
        return "Update Success";
    }

    @DeleteMapping("/{commentId}")
    public String deleteById(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return "Delete Success";
    }

}
