package com.example.apis.model;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    User findByPassword(String password);
}
