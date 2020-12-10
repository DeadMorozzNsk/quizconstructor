package com.opencode.quizconstructor.backend.services;

import com.opencode.quizconstructor.backend.domain.Answer;
import com.opencode.quizconstructor.backend.domain.Question;
import com.opencode.quizconstructor.backend.domain.Quiz;
import com.opencode.quizconstructor.backend.repositories.AnswerRepo;
import com.opencode.quizconstructor.backend.repositories.QuestionRepo;
import com.opencode.quizconstructor.backend.repositories.QuizRepo;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Quiz copyQuizProperties(Quiz sourceQuiz, Quiz targetQuiz) {
        BeanUtils.copyProperties(sourceQuiz, targetQuiz, "id", "questions");
        if (sourceQuiz.getQuestions() == null) sourceQuiz.setQuestions(new ArrayList<>());
        targetQuiz.getQuestions().removeIf(current -> !sourceQuiz.getQuestions().contains(current));
        for (Question q : sourceQuiz.getQuestions()) {
            if (targetQuiz.getQuestions().contains(q)) { /* equals & hashcode by "id" */
                int idx = targetQuiz.getQuestions().indexOf(q);
                if (idx > -1) {
//                    BeanUtils.copyProperties(q, targetQuiz.getQuestions().get(idx), "id");
                    Question targetQuestion = targetQuiz.getQuestions().get(idx);
                    copyQuestionProperties(q, targetQuestion, false);
//                    questionRepo.save(copyQuestionProperties(q, targetQuestion));
                }
            } else {
                Question newQuestion = new Question();
//                BeanUtils.copyProperties(q, newQuestion, "id");
                newQuestion.setAnswers(new ArrayList<>());
//                copyQuestionProperties(q, newQuestion);
//                targetQuiz.getQuestions().add(questionRepo.save(copyQuestionProperties(q, newQuestion)));
                targetQuiz.getQuestions().add(copyQuestionProperties(q, newQuestion, true));
            }
        }
        return quizRepo.save(targetQuiz);
    }

    @Transactional
    public Question copyQuestionProperties(Question sourceQuestion,
                                           Question targetQuestion,
                                           boolean isNewQuestion) {
        BeanUtils.copyProperties(sourceQuestion, targetQuestion, "id", "answers");
        targetQuestion.getAnswers().removeIf(
                answer -> !sourceQuestion.getAnswers().contains(answer));
        for (Answer a : sourceQuestion.getAnswers()) {
            if (targetQuestion.getAnswers().contains(a)) {
                int idx = targetQuestion.getAnswers().indexOf(a);
                if (idx > -1) {
                    Answer targetAnswer = new Answer();
                    targetAnswer =  targetQuestion.getAnswers().get(idx);
                    BeanUtils.copyProperties(a, targetAnswer, "id");
                }
            } else {
                Answer newAnswer = new Answer();
                BeanUtils.copyProperties(a, newAnswer, "id");
                newAnswer.setQuestion(targetQuestion);
//                targetQuestion = questionRepo.save(targetQuestion);
                targetQuestion = isNewQuestion ? questionRepo.save(targetQuestion) : targetQuestion;
                targetQuestion.getAnswers().add(answerRepo.save(newAnswer));
            }
        }
        return !isNewQuestion ? questionRepo.save(targetQuestion) : targetQuestion ;
    }

    public void deleteQuestion(Question question) {
        questionRepo.delete(question);
    }
}
