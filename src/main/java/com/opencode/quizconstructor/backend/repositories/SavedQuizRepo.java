package com.opencode.quizconstructor.backend.repositories;

import com.opencode.quizconstructor.backend.domain.SavedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedQuizRepo extends JpaRepository<SavedQuiz, Integer> {

}
