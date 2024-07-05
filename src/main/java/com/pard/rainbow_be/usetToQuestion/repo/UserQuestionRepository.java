package com.pard.rainbow_be.usetToQuestion.repo;

import com.pard.rainbow_be.usetToQuestion.entity.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {
    List<UserQuestion> findByUserId(UUID userId);
    UserQuestion findByUserIdAndQuestionId(UUID userId, Long questionId);
}