package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.opencode.quizconstructor.backend.domain.Answer;
import com.opencode.quizconstructor.backend.domain.Question;
import com.opencode.quizconstructor.backend.repositories.AnswerRepo;
import com.opencode.quizconstructor.backend.repositories.QuestionRepo;
import com.opencode.quizconstructor.backend.services.QuizService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz/questions")
public class QuestionController {
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;
    private final QuizService quizService;

    @Autowired
    public QuestionController(QuestionRepo questionRepo,
                              AnswerRepo answerRepo,
                              QuizService quizService) {
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
        this.quizService = quizService;
    }


    @GetMapping("/all")
    public List<Question> getAll() {
        return questionRepo.findAll();
    }

    @PostMapping("{id}/add_answer")
    public void addAnswerToQuestion(@PathVariable("id") Question question,
                                    @RequestBody AnswerRequestBody answerRequestQuery){
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerRequestQuery, answer, "id");
        answer.setQuestion(question);
        answerRepo.save(answer);
    }

    @DeleteMapping("{id}/delete")
    public void deleteQuestion(@PathVariable("id") Question question) {
        quizService.deleteQuestion(question);
    }

}

@Data
class AnswerRequestBody {
    private int id;
    private String text;
    private boolean isCorrect;
}
