package com.opencode.quizconstructor.backend.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static com.opencode.quizconstructor.backend.config.JsonViewConfig.*;

@Entity
@Data
@Table(name = "questions")
@NoArgsConstructor
@ToString(of = {"id", "text", "questionType"})
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo( scope = Question.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(OnlyId.class)
    private int id;

    @Column(name = "question_text")
    @JsonView(IdName.class)
    private String text;

    @JsonView({JsonViewConfig.FullObject.class})
    private int position;

    @Column(name = "question_type")
    @Enumerated(EnumType.ORDINAL)
    @JsonView(FullObject.class)
    private QuestionChoiseType questionType;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonView(FullObject.class)
    private Quiz quiz;

    @OneToMany(mappedBy = "question",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonView(FullObject.class)
    List<Answer> answers;

//    public Question(Quiz quiz, String text,
//                    QuestionChoiseType questionType, List<Answer> answers) {
//        this.quiz = quiz;
//        this.text = text;
//        this.questionType = questionType;
//        this.answers = answers;
//    }
}
