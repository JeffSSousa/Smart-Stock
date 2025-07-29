package com.jeffersonsousa.smartstock.controller;

import java.util.List;

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

import com.jeffersonsousa.smartstock.dto.CategoryRequestDTO;
import com.jeffersonsousa.smartstock.dto.CategoryResponseDTO;
import com.jeffersonsousa.smartstock.service.CategoryService;

@RestController
@RequestMapping("category")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@PostMapping
	public ResponseEntity<Void> insertCategory(@RequestBody CategoryRequestDTO dto){
		service.createCategory(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
		CategoryResponseDTO category = service.findById(id);
		return ResponseEntity.ok().body(category);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryResponseDTO>> getCategories(){
		List<CategoryResponseDTO> list = service.getAllCategories();
		return ResponseEntity.ok().body(list);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO dto){
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	} 
}
