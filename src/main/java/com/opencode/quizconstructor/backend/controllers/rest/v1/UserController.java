package com.opencode.quizconstructor.backend.controllers.rest.v1;

import com.fasterxml.jackson.annotation.JsonView;
import com.opencode.quizconstructor.backend.config.JsonViewConfig;
import com.opencode.quizconstructor.backend.domain.User;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import com.opencode.quizconstructor.backend.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepo userRepo;
    private final UserService userService;

    @Autowired
    public UserController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/all")
    @JsonView(JsonViewConfig.IdName.class)
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(JsonViewConfig.FullObject.class)
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PutMapping("{id}/roles")
    @Transactional
    public void setUserRoles(@PathVariable("id") User userDb,
                           @RequestBody int[] rolesIds) {
        User user = new User();
        BeanUtils.copyProperties(userDb, user, "id, roles");
        user.setRoles(userService.setUserRoles(rolesIds, user.getRoles()));
        userRepo.save(user);
    }

}
