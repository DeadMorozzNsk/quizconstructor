package com.opencode.quizconstructor.backend.repositories;

import com.opencode.quizconstructor.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
