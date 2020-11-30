package com.opencode.quizconstructor.backend.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Entity
@Data
@Table(name = "quiz")
@NoArgsConstructor
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo(scope = Quiz.class,
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
    @JoinColumn(name = "author_id", nullable=false)
    @JsonView(JsonViewConfig.FullObject.class)
    private User author;

    @OneToMany(mappedBy = "quiz",
//            orphanRemoval = true,
            cascade = CascadeType.ALL)
            //fetch = FetchType.EAGER)
    @JsonView(JsonViewConfig.FullObject.class)
    private List<Question> questions;

//    public Quiz(String name, User author, List<Question> questions) {
//        this.name = name;
//        this.author = author;
//        this.questions = questions;
//    }

    @JsonView(JsonViewConfig.CustomView.class)
    @JsonAnyGetter
    public Map<String, Object> getTableView() {
        Map<String, Object> params = new TreeMap<>();
        params.put("id", this.getId());
        params.put("name", this.getName());
        params.put("authorName", this.getAuthor().getUsername());
        params.put("questionsCount", this.getQuestions().size());
        return params;
    }

}
