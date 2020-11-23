package com.opencode.quizconstructor.backend.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GenerationType;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonView(JsonViewConfig.OnlyId.class)
    private int id;

    @Column(name = "answer_text")
    @JsonView(JsonViewConfig.IdName.class)
    private String text;

    @Column(name = "correct")
    @JsonView(JsonViewConfig.FullObject.class)
    private boolean isCorrect;

    @ManyToOne(optional = false,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")//, referencedColumnName = "id")
    @JsonView(JsonViewConfig.FullObject.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Question question;

    public Answer(Question question, String text, boolean isCorrect) {
        this.question = question;
        this.text = text;
        this.isCorrect = isCorrect;
    }
}
