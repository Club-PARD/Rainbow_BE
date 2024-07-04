package com.pard.rainbow_be.question.service;

import com.pard.rainbow_be.question.dto.QuestionDto;
import com.pard.rainbow_be.question.entity.Question;
import com.pard.rainbow_be.question.repo.QuestionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;

    public void createQuestion(QuestionDto.Create dto){
        this.questionRepo.save(Question.entity(dto));
    }

    
}
