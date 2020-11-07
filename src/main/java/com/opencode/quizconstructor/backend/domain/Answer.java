package com.opencode.quizconstructor.backend.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answers")
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")//, referencedColumnName = "id")
    private Question question;
    @Column(name = "answer_text")
    private String text;
    @Column(name = "correct")
    private boolean isCorrect;

    public Answer(Question question, String text, boolean isCorrect) {
        this.question = question;
        this.text = text;
        this.isCorrect = isCorrect;
    }
}
