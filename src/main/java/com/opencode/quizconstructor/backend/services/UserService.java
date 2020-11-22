package com.opencode.quizconstructor.backend.services;

import com.opencode.quizconstructor.backend.domain.User;
import com.opencode.quizconstructor.backend.domain.UserRole;
import com.opencode.quizconstructor.backend.repositories.RoleRepo;
import com.opencode.quizconstructor.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Boolean createNewUser(User newUser) {
        User existingUser = userRepo.findByUsername(newUser.getUsername());
        boolean result = true;
        if (existingUser != null)
            result = false;
        else
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setRoles(new ArrayList<>());
            newUser.getRoles().add(roleRepo.findByName("ROLE_USER")); /* хардкод каждый новый юзер - не админ */
            userRepo.save(newUser);
        return result;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("User %s not found", username)
            );
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(
            Collection<UserRole> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
