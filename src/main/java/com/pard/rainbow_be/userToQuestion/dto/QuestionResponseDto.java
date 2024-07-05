package com.pard.rainbow_be.userToQuestion.dto;

import com.pard.rainbow_be.question.dto.QuestionDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionResponseDto {
    private QuestionDto.Read question;
    private boolean answered;
}
