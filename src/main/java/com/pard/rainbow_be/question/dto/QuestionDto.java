package com.pard.rainbow_be.question.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.question.entity.Question;
import lombok.Getter;
import lombok.Setter;


public class QuestionDto {
    @Getter
    @Setter
    public static class Create{
        private String questionText;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Read{
        private Long id;
        private String questionText;

        public Read(Question question){
            this.id = question.getId();
            this.questionText = question.getQuestionText();
        }
    }
}
