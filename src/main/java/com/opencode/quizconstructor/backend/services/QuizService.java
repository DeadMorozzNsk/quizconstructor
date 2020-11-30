package com.opencode.quizconstructor.backend.services;

import com.opencode.quizconstructor.backend.domain.Question;
import com.opencode.quizconstructor.backend.domain.Quiz;
import com.opencode.quizconstructor.backend.repositories.AnswerRepo;
import com.opencode.quizconstructor.backend.repositories.QuestionRepo;
import com.opencode.quizconstructor.backend.repositories.QuizRepo;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

@Service
public class QuizService {
    private final QuizRepo quizRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;
    private final UserRepo userRepo;

    @Autowired
    public QuizService(QuizRepo quizRepo,
                       QuestionRepo questionRepo,
                       AnswerRepo answerRepo,
                       UserRepo userRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
        this.userRepo = userRepo;
    }

    public Quiz addQuestionToQuiz(Quiz quiz, Question question) {
        Quiz quizFromDb = quizRepo.findById(quiz.getId());
        if (quizFromDb == null) {
            quizFromDb = new Quiz();
            quizFromDb.setQuestions(new ArrayList<>());
            quizFromDb.getQuestions().add(question);
        } else {
            if (quizFromDb.getQuestions().contains(question)) {
                questionRepo.save(question);
            } else {
                quizFromDb.getQuestions().add(question);
            }
        }
        return quizRepo.save(quizFromDb);
    }

    public void deleteQuestion(Question question) {
        questionRepo.delete(question);
    }
}
