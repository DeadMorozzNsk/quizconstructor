package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.opencode.quizconstructor.backend.domain.Answer;
import com.opencode.quizconstructor.backend.domain.Question;
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

    @GetMapping("/all")
    //@CrossOrigin(origins = "http://localhost:8080")
    public List<Quiz> getAll() {
        return quizRepo.findAll();
    }

    @PostMapping("/add_quiz")
    //@CrossOrigin(origins = "http://localhost:8080")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @PostMapping("/add_question")
    public void addQuestionToQuiz(Quiz quiz,
                                  @RequestBody Question question) {
        quiz.getQuestions().add(question);
    }

    @PostMapping("/add_answer")
    public void addAnswerToQuestion(Question question,
                                    @RequestBody Answer answer){
        question.getAnswers().add(answer);
    }

}
