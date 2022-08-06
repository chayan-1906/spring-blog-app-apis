package com.pdas.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pdas.blog.entities.Category;
import com.pdas.blog.entities.Post;
import com.pdas.blog.entities.User;
import com.pdas.blog.exceptions.ResourceNotFoundException;
import com.pdas.blog.playloads.PostDto;
import com.pdas.blog.playloads.PostResponse;
import com.pdas.blog.repositories.CategoryRepo;
import com.pdas.blog.repositories.PostRepo;
import com.pdas.blog.repositories.UserRepo;
import com.pdas.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post postToBeCreated = this.postRepo.save(post);

		return this.modelMapper.map(postToBeCreated, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post postToBeUpdated = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		postToBeUpdated.setTitle(postDto.getTitle());
		postToBeUpdated.setContent(postDto.getContent());
		postToBeUpdated.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(postToBeUpdated);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post postToBeDeleted = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));
		this.postRepo.delete(postToBeDeleted);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
		Sort sort = null;
		if (sortDirection.equalsIgnoreCase("ascending")) {
			sort = Sort.by(sortBy).ascending();
		} else if (sortDirection.equalsIgnoreCase("descending")) {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> allPosts = pagePost.getContent();

		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findAllPostsByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepo.findAllPostsByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<Post> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
