package com.pard.rainbow_be.question.entity;


import com.pard.rainbow_be.question.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;

    public static Question entity(QuestionDto.Create dto){
        return Question.builder()
                .questionText(dto.getQuestionText())
                .build();
    }
}
