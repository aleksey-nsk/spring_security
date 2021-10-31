package com.spring.example7.repository;

import com.spring.example7.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, String> {

    List<MyUser> findAll();
}
