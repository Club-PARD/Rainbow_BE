package com.pard.rainbow_be.comment.repo;

import com.pard.rainbow_be.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}
