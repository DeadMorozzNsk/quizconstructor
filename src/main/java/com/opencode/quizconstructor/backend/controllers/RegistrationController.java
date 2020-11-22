package com.opencode.quizconstructor.backend.controllers;

import com.opencode.quizconstructor.backend.domain.User;
import com.opencode.quizconstructor.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        Boolean created = userService.createNewUser(user);
        if (!created) {
           model.put("message", "User alreary exists!");
        } else
            return "redirect:/login";
        return "registration";
    }
}
