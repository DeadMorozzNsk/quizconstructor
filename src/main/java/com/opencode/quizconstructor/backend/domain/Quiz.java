package com.opencode.quizconstructor.backend.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "quiz")
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JsonViewConfig.OnlyId.class)
    private int id;

    @JsonView(JsonViewConfig.IdName.class)
    private String name;

    @ManyToOne(//cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonView(JsonViewConfig.FullObject.class)
    @JsonIdentityReference(alwaysAsId = true)
    private User author;

    @OneToMany(mappedBy = "quiz",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonView(JsonViewConfig.FullObject.class)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Question> questions;

    public Quiz(String name, User author, List<Question> questions) {
        this.name = name;
        this.author = author;
        this.questions = questions;
    }
}
