package com.pard.rainbow_be.userToQuestion.repo;

import com.pard.rainbow_be.comment.entity.Comment;
import com.pard.rainbow_be.question.entity.Question;
import com.pard.rainbow_be.userToQuestion.entity.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {
    List<UserQuestion> findByUserId(UUID userId);
    UserQuestion findByUserIdAndQuestionId(UUID userId, Long questionId);
    Optional<UserQuestion> findByUserIdAndQuestionQuestionTextContains(UUID user_id, String question_questionText);
    void deleteByUserId(UUID id);
}
