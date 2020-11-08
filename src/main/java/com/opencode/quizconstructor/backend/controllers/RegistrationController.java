package com.opencode.quizconstructor.backend.controllers;

import com.opencode.quizconstructor.backend.domain.User;
import com.opencode.quizconstructor.backend.domain.UserRole;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        userRepo.save(user);
        return "redirect:/login";
    }
}
