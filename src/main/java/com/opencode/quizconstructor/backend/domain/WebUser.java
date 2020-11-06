package com.opencode.quizconstructor.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private int role;

    public WebUser(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role.ordinal();
    }
}
