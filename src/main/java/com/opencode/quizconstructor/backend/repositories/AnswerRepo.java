package com.opencode.quizconstructor.backend.repositories;

import com.opencode.quizconstructor.backend.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {
}
