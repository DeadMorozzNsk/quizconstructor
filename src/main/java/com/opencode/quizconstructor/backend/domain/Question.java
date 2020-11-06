package com.opencode.quizconstructor.backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quiz_id")
    private int quiz;
    @Column(name = "question_text")
    private String text;
    @Column(name = "question_type")
    private int questionType;
}
