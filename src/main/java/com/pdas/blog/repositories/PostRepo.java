package com.pdas.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pdas.blog.entities.Category;
import com.pdas.blog.entities.Post;
import com.pdas.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findAllPostsByUser(User user);

	List<Post> findAllPostsByCategory(Category category);

	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);

}
