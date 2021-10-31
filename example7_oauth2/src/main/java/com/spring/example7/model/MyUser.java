package com.spring.example7.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "my_user")
@Data
public class MyUser {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Нету GeneratedValue потому что айдишники придут от Гугла
//    @Column(name = "id")
    private String id;

//    @Column(name = "username")
//    private String username;

//    @Column(name = "password")
//    private String password;

//    @Column(name = "position")
//    private String position; // должность

//    @Column(name = "role")
//    private String role;

    @Column(name = "name")
    private String username;

    private String userpic;
    private String email;
    private String gender;
    private String locale;

//    @Column(name = "last_visit")
//    private LocalDateTime lastVisit; // дата последнего визита пользователя
}
