package com.spring.example3.repository;

import com.spring.example3.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByUsername(String username);

    @Override
    List<MyUser> findAll();
}
