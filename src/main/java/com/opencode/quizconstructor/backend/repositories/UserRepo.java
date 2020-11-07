package com.opencode.quizconstructor.backend.repositories;

import com.opencode.quizconstructor.backend.domain.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<WebUser, Integer> {
}
