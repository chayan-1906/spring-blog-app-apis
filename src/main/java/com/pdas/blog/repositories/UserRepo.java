package com.pdas.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdas.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
