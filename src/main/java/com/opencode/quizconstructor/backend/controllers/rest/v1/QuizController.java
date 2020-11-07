package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.opencode.quizconstructor.backend.domain.Quiz;
import com.opencode.quizconstructor.backend.repositories.AnswerRepo;
import com.opencode.quizconstructor.backend.repositories.QuestionRepo;
import com.opencode.quizconstructor.backend.repositories.QuizRepo;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("anketa")
public class QuizController {
    private final UserRepo userRepo;
    private final QuizRepo quizRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;

    @Autowired
    public QuizController(UserRepo userRepo, QuizRepo quizRepo,
                          QuestionRepo questionRepo, AnswerRepo answerRepo) {
        this.userRepo = userRepo;
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }

    @GetMapping
    public List<Quiz> getAll() {
        return quizRepo.findAll();
    }

    @PostMapping
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @DeleteMapping(params = {"id"})
    public void deleteQuiz(@RequestBody Quiz quiz){
        Quiz q = quizRepo.getOne(quiz.getId());
        quizRepo.delete(q);
    }

    @DeleteMapping
    public void deleteQuizById(@RequestParam(name = "id") Integer id){
        quizRepo.deleteById(id);
    }

}
