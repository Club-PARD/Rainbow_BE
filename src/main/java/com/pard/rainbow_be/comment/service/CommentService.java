package com.pard.rainbow_be.comment.service;


import com.pard.rainbow_be.comment.dto.CommentCreateDTO;
import com.pard.rainbow_be.comment.dto.CommentReadDTO;
import com.pard.rainbow_be.comment.dto.CommentUpdateDTO;
import com.pard.rainbow_be.comment.entity.Comment;
import com.pard.rainbow_be.comment.repo.CommentRepo;
import com.pard.rainbow_be.post.dto.PostReadDTO;
import com.pard.rainbow_be.post.entity.Post;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    public void createComment(CommentCreateDTO commentCreateDTO, UUID ownerId, UUID writerId){
        User owner = userRepo.findById(ownerId).orElseThrow();
        User writer = userRepo.findById(writerId).orElseThrow();
        commentRepo.save(Comment.toEntity(commentCreateDTO, owner, writer));
    }

    public List<CommentReadDTO> readAll(UUID ownerId){
        return commentRepo.findAllByOwnerId(ownerId)
                .stream()
                .map(CommentReadDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CommentReadDTO> readOne(UUID ownerId, Long commentId){
        return commentRepo.findByOwnerIdAndCommentId(ownerId, commentId)
                .map(CommentReadDTO::new);
    }

    public Integer countOwnerComment(UUID ownerId){
        List<Comment> comments = commentRepo.findAllByOwnerId(ownerId)
                .stream()
                .map(CommentReadDTO::new)
                .collect(Collectors.toList());
        return comments.size();
    }

    public void updateByWriterIdAndCommentId(UUID writerId, Long commentId, CommentUpdateDTO commentUpdateDTO){
        Comment comment = commentRepo.findByOwnerIdAndCommentId(writerId, commentId).get();
        comment.update(commentUpdateDTO);
        commentRepo.save(comment);
    }


    @Transactional
    public boolean deleteCommentByWriterIdAndCommentId(UUID writerId,Long commentId){
        Optional<Comment> comment = commentRepo.findByWriterIdAndCommentId(writerId, commentId);
        if(comment.isPresent()){
            commentRepo.delete(comment.get());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteCommentByOwnerIdAndCommentId(UUID ownerId,Long commentId){
        Optional<Comment> comment = commentRepo.findByWriterIdAndCommentId(ownerId, commentId);
        if(comment.isPresent()){
            commentRepo.delete(comment.get());
            return true;
        }
        return false;
    }

}
