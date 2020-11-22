package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.opencode.quizconstructor.backend.domain.UserRole;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("login1")
public class LoginController {
    private final UserRepo userRepo;

    @Autowired
    public LoginController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/roles")
    public List<UserRole> getRoles() {
        List<UserRole> result = new ArrayList<>();
        //Collections.addAll(result, UserRole.values());
        return result;
    }
}
