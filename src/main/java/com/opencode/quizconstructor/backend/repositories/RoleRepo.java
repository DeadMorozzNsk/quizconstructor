package com.opencode.quizconstructor.backend.repositories;

import com.opencode.quizconstructor.backend.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface RoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByName(String name);

    Collection<UserRole> findAllByIdIn(Collection<Integer> id);
}
