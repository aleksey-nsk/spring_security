package com.spring.example4.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "my_user")
@Data
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "position")
    private String position; // должность

    @Column(name = "role")
    private String role;
}
