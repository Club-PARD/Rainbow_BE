package com.pard.rainbow_be.comment.service;


import com.pard.rainbow_be.comment.dto.CommentCreateDTO;
import com.pard.rainbow_be.comment.dto.CommentReadDTO;
import com.pard.rainbow_be.comment.dto.CommentUpdateDTO;
import com.pard.rainbow_be.comment.entity.Comment;
import com.pard.rainbow_be.comment.repo.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    public void createComment(CommentCreateDTO commentCreateDTO){
        commentRepo.save(Comment.toEntity(commentCreateDTO));
    }

    //need to understand the meaning of the below code
    public List<CommentReadDTO> readAll(){
        return commentRepo.findAll()
                .stream()
                .map(CommentReadDTO::new)
                .collect(Collectors.toList());
    }
    public CommentReadDTO findById(Long commentId){
        return new CommentReadDTO(commentRepo.findById(commentId).orElseThrow());
    }

    public void updateById(Long commentId, CommentUpdateDTO commentUpdateDTO){
        Comment comment = commentRepo.findById(commentId).get();
        comment.update(commentUpdateDTO);
        commentRepo.save(comment);
    }

    public void deleteById(Long commentId){
        commentRepo.deleteById(commentId);
    }

}
