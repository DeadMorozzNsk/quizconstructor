package com.opencode.quizconstructor.backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

}
