package com.opencode.quizconstructor.backend.controllers;

import com.opencode.quizconstructor.backend.domain.User;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller("home")
public class MainPageController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return "home";
    }


}
