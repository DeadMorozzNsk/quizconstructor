package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.opencode.quizconstructor.backend.domain.User;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/all")
    public List<User> addUser() {
        return userRepo.findAll();
    }
}
