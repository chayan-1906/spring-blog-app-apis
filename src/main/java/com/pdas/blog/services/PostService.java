package com.pdas.blog.services;

import java.util.List;

import com.pdas.blog.entities.Post;
import com.pdas.blog.playloads.PostDto;
import com.pdas.blog.playloads.PostResponse;

public interface PostService {

	// create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update post
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete post
	void deletePost(Integer postId);

	// get all posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

	// get single post
	PostDto getPostById(Integer postId);

	// get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);

	// get all posts by user
	List<PostDto> getPostsByUser(Integer userId);

	// search post
	List<PostDto> searchPosts(String keyword);
}
