package com.pdas.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdas.blog.entities.Comment;
import com.pdas.blog.entities.Post;
import com.pdas.blog.exceptions.ResourceNotFoundException;
import com.pdas.blog.playloads.CommentDto;
import com.pdas.blog.repositories.CommentRepo;
import com.pdas.blog.repositories.PostRepo;
import com.pdas.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Comment commentToBeSaved = this.modelMapper.map(commentDto, Comment.class);
		commentToBeSaved.setPost(post);
		Comment savedComment = this.commentRepo.save(commentToBeSaved);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment commentToBeDeleted = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.commentRepo.delete(commentToBeDeleted);
	}

}
