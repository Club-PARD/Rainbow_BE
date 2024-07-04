package com.pard.rainbow_be.usetToQuestion.service;

import com.pard.rainbow_be.usetToQuestion.entity.UserQuestion;
import com.pard.rainbow_be.usetToQuestion.repo.UserQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserQuestionService {
    private final UserQuestionRepository userQuestionRepository;

    public List<UserQuestion> userQuestionsFindByUserId(UUID userId){
        return userQuestionRepository.findByUserId(userId);
    }
}
