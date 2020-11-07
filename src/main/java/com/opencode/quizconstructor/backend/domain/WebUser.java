package com.opencode.quizconstructor.backend.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private int role;

    public WebUser(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;//.ordinal();
    }

    public WebUser(String username, String password, UserRole role) {
        this(username, password, role.ordinal());
    }
}
