package com.pard.rainbow_be.question.repo;

import com.pard.rainbow_be.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    Question findByQuestionText(String questionText);
}
