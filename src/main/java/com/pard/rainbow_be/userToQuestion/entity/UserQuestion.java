package com.pard.rainbow_be.userToQuestion.entity;

import com.pard.rainbow_be.question.entity.Question;
import com.pard.rainbow_be.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "UserToQuestion")
public class UserQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private boolean answered;

    public void answerQuestion(Boolean answered) {
        this.answered = answered;
    }
}
