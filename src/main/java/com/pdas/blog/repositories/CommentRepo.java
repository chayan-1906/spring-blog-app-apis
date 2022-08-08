package com.pdas.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdas.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
