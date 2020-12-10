package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.fasterxml.jackson.annotation.JsonView;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;
import com.opencode.quizconstructor.backend.domain.Question;
import com.opencode.quizconstructor.backend.domain.Quiz;
import com.opencode.quizconstructor.backend.repositories.QuizRepo;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import com.opencode.quizconstructor.backend.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    private final QuizRepo quizRepo;
    private final QuizService quizService;
    private final UserRepo userRepo;

    @Autowired
    public QuizController(QuizRepo quizRepo, QuizService quizService, UserRepo userRepo) {
        this.quizRepo = quizRepo;
        this.quizService = quizService;
        this.userRepo = userRepo;
    }

    @GetMapping("/all")
    //@CrossOrigin(origins = "http://localhost:8080")
    @JsonView(JsonViewConfig.FullObject.class)
    public List<Quiz> getAll() {
        return quizRepo.findAll();
    }

    @GetMapping("/all/simplified")
    @JsonView(JsonViewConfig.CustomView.class)
    public List<Quiz> getAllTableView() {
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
    public Quiz addQuestionToQuiz(@PathVariable("id") Quiz quiz,
                                  @RequestBody Question question) {
        return quizService.addQuestionToQuiz(quiz, question);
    }

    @PutMapping("{id}/delete_question/{qid}")
    public Quiz deleteQuestionFromQuiz(@PathVariable("id") Quiz quiz,
                                       @PathVariable("qid") Question question) {
        quiz.getQuestions().remove(question);
        return quizRepo.save(quiz);
    }

    @PutMapping("{id}")
    public Quiz editQuiz(@PathVariable("id") Quiz quizFromDb, @RequestBody Quiz quiz) {
//        BeanUtils.copyProperties(quiz, quizFromDb, "id");
        quizService.copyQuizProperties(quiz, quizFromDb);
        return quizRepo.save(quizFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteQuiz(@PathVariable("id") Quiz quiz) {
        quizRepo.delete(quiz);
    }

}
