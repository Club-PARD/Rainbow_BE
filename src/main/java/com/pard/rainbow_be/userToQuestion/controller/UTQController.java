package com.pard.rainbow_be.userToQuestion.controller;

import com.pard.rainbow_be.userToQuestion.dto.QuestionResponseDto;
import com.pard.rainbow_be.userToQuestion.service.UserQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class UTQController {
    private final UserQuestionService userQuestionService;
//    @PostMapping("/questions/{userId}/{questionId}/answer")
//    @Operation(summary = "해당 질문 사용 했음을 알림", description = "userId와 Question Id를 준다면 해당 질문 사용했다는 의미로 true 바꾼다.")
//    public void answerQuestion(@PathVariable UUID userId, @PathVariable Long questionId) {
//        userQuestionService.answerQuestion(userId, questionId, true);
//        log.info("📍 질문이 사용되었다");
//    }

    @GetMapping("/questions/{userId}")
    @Operation(summary = "해당 유저의 질문 리스트", description = "해당 id를 갖은 유저가 사용한 혹은 사용하지 않은 질문 전체를 보내준다.")
    public List<QuestionResponseDto> questionList(@PathVariable UUID userId){
        log.info("📍 모든 질문 리스트 보내줌");
        return userQuestionService.questionList(userId);
    }

}
