package com.pard.rainbow_be.question.controller;

import com.pard.rainbow_be.question.dto.QuestionDto;
import com.pard.rainbow_be.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/question")
    @Operation(summary = "질문 등록", description = "여기서 쓰시면 됩니다.")
    public void createQuestion (@RequestBody QuestionDto.Create dto){
        log.info("새로 등록");
        questionService.createQuestion(dto);
    }

//    @GetMapping("user/{userId}/question/{questionId}")
//    public
}
