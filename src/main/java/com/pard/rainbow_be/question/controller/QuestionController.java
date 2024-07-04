package com.pard.rainbow_be.question.controller;

import com.pard.rainbow_be.question.dto.QuestionDto;
import com.pard.rainbow_be.question.entity.Question;
import com.pard.rainbow_be.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;


}
