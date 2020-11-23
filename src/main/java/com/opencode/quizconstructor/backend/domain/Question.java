package com.opencode.quizconstructor.backend.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "questions")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JsonViewConfig.OnlyId.class)
    private int id;

    @Column(name = "question_text")
    @JsonView(JsonViewConfig.IdName.class)
    private String text;

    @Column(name = "question_type")
    @Enumerated(EnumType.ORDINAL)
    @JsonView(JsonViewConfig.FullObject.class)
    private QuestionChoiseType questionType;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonView(JsonViewConfig.FullObject.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Quiz quiz;

    @OneToMany(mappedBy = "question",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonView(JsonViewConfig.FullObject.class)
    @JsonIdentityReference(alwaysAsId = true)
    List<Answer> answers;

    public Question(Quiz quiz, String text,
                    QuestionChoiseType questionType, List<Answer> answers) {
        this.quiz = quiz;
        this.text = text;
        this.questionType = questionType;
        this.answers = answers;
    }
}
