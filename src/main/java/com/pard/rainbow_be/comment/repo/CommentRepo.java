package com.pard.rainbow_be.comment.repo;

import com.pard.rainbow_be.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOwnerId(UUID ownerId);
    Optional<Comment> findByOwnerIdAndCommentId(UUID ownerId, Long commentId);
    Optional<Comment> findByWriterIdAndCommentId(UUID writerId, Long commentId);

    void deleteByOwnerId(UUID id);
}
