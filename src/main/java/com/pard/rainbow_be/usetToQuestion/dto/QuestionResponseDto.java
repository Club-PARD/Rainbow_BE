package com.pard.rainbow_be.usetToQuestion.dto;

import com.pard.rainbow_be.question.dto.QuestionDto;
import com.pard.rainbow_be.question.entity.Question;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionResponseDto {
    private QuestionDto.Read question;
    private boolean answered;
}
