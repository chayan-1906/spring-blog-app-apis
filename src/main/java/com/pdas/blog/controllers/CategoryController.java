package com.pdas.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdas.blog.playloads.ApiResponse;
import com.pdas.blog.playloads.CategoryDto;
import com.pdas.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategoryDto, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {
		CategoryDto updateCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateCategoryDto, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully", true),
				HttpStatus.OK);
	}

	// get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	// get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> categories = this.categoryService.getCategories();
		return ResponseEntity.ok(categories);
	}
}
