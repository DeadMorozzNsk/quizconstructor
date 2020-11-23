package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.fasterxml.jackson.annotation.JsonView;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;
import com.opencode.quizconstructor.backend.domain.Answer;
import com.opencode.quizconstructor.backend.domain.Question;
import com.opencode.quizconstructor.backend.domain.Quiz;
import com.opencode.quizconstructor.backend.repositories.QuizRepo;
import com.opencode.quizconstructor.backend.services.QuizService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    private final QuizRepo quizRepo;
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizRepo quizRepo, QuizService quizService) {
        this.quizRepo = quizRepo;
        this.quizService = quizService;
    }

    @GetMapping("/all")
    //@CrossOrigin(origins = "http://localhost:8080")
    @JsonView(JsonViewConfig.FullObject.class)
    public List<Quiz> getAll() {
        return quizRepo.findAll();
    }

    @PostMapping("/add_quiz")
    //@CrossOrigin(origins = "http://localhost:8080")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @GetMapping("{id}")
    @JsonView(JsonViewConfig.FullObject.class)
    public Quiz getById(@PathVariable("id") Quiz quiz) {
        return quiz;
    }

    @PostMapping("{id}/add_question")
    @JsonView(JsonViewConfig.FullObject.class)
    public void addQuestionToQuiz(@PathVariable("id") Quiz quiz,
            @RequestBody Question question) {
//        quiz.getQuestions().add(question);
        quizService.addQuestionToQuiz(quiz, question);
    }

    @PutMapping("{id}/delete_question/{qid}")
    public void deleteQuestionFromQuiz(@PathVariable("id") Quiz quiz,
                                       @PathVariable("qid") Question question) {
        quiz.getQuestions().remove(question);
        quizRepo.save(quiz);
    }

    @PutMapping("{id}")
    public void editQuiz(@PathVariable("id") Quiz quizFromDb, @RequestBody Quiz quiz) {
        BeanUtils.copyProperties(quiz, quizFromDb, "id");
        quizRepo.save(quiz);
    }

    @DeleteMapping("{id}")
    public void deleteQuiz(@PathVariable("id") Quiz quiz) {
        quizRepo.delete(quiz);
    }

}
