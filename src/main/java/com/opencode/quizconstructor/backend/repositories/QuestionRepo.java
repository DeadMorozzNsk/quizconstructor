package com.opencode.quizconstructor.backend.repositories;

import com.opencode.quizconstructor.backend.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Integer> {
}
