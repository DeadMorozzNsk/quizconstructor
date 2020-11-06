package com.opencode.quizconstructor.backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
    @Column(name = "answer_text")
    private String text;
    @Column(name = "correct")
    private boolean isCorrect;
}
