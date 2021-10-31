package com.spring.example7.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "my_user")
@Data
public class MyUser {

    // Отсутствует @GeneratedValue, потому что id придёт от Google
    @Id
    private String id;

    @Column(name = "name")
    private String username;

    private String userpic;
    private String email;
    private String gender;
    private String locale;
}
