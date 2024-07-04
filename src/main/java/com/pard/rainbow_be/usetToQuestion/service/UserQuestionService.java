package com.pard.rainbow_be.usetToQuestion.service;

import com.pard.rainbow_be.question.dto.QuestionDto;
import com.pard.rainbow_be.question.entity.Question;
import com.pard.rainbow_be.question.repo.QuestionRepo;
import com.pard.rainbow_be.usetToQuestion.dto.QuestionResponseDto;
import com.pard.rainbow_be.usetToQuestion.entity.UserQuestion;
import com.pard.rainbow_be.usetToQuestion.repo.UserQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQuestionService {
    private final UserQuestionRepository userQuestionRepository;
    private final QuestionRepo questionRepo;

    public List<QuestionResponseDto> questionList(UUID userId) {
        List<UserQuestion> userQuestions = userQuestionRepository.findByUserId(userId);
        return userQuestions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private QuestionResponseDto convertToDto(UserQuestion userQuestion) {
        QuestionDto.Read questionDto = new QuestionDto.Read(userQuestion.getQuestion());
        return new QuestionResponseDto(
                questionDto,
                userQuestion.isAnswered()
        );
    }

    @Transactional
    public void answerQuestion(UUID userId, Long questionId, Boolean answered) {
        UserQuestion userQuestion = userQuestionRepository.findByUserIdAndQuestionId(userId, questionId);
        if (userQuestion != null) {
            userQuestion.setAnswered(answered);
            userQuestionRepository.save(userQuestion);
        }
    }
}
