package com.opencode.quizconstructor.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "questions")
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @Column(name = "question_text")
    private String text;
    @Column(name = "question_type")
    private int questionType;

    @OneToMany
    @JoinColumn(name = "question_id")
    List<Answer> answers;

    public Question(Quiz quiz, String text, QuestionChoiseType questionType) {
        this.quiz = quiz;
        this.text = text;
        this.questionType = questionType.ordinal();
    }
}
