package com.opencode.quizconstructor.backend.repositories;

import com.opencode.quizconstructor.backend.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<Quiz, Integer> {

    Quiz findById(int id);
}
