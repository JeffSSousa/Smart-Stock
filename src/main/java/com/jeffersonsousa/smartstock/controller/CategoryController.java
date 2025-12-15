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

import com.jeffersonsousa.smartstock.dto.category.CategoryRequestDTO;
import com.jeffersonsousa.smartstock.dto.category.CategoryResponseDTO;
import com.jeffersonsousa.smartstock.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("category")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@Operation(description = "Cria uma categoria de produtos.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cria a categoria")
	})
	@PostMapping
	public ResponseEntity<Void> insertCategory(@RequestBody CategoryRequestDTO dto){
		service.createCategory(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(description = "Busca categoria pelo Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna a categoria pelo Id."),
			@ApiResponse(responseCode = "404", description = "Não encontrou a categoria.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
		CategoryResponseDTO category = service.findById(id);
		return ResponseEntity.ok().body(category);
	}
	
	@Operation(description = "Busca todas as categorias.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todas as categorias."),
	})
	@GetMapping
	public ResponseEntity<List<CategoryResponseDTO>> getCategories(){
		List<CategoryResponseDTO> list = service.getAllCategories();
		return ResponseEntity.ok().body(list);
	}
	
	
	@Operation(description = "Edita a categoria pelo Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna a categoria editada."),
			@ApiResponse(responseCode = "404", description = "Não encontrou a categoria.")
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO dto){
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	
	@Operation(description = "Deleta uma categoria pelo Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Retorna que a categoria foi deletada."),
			@ApiResponse(responseCode = "404", description = "Não encontrou a categoria."),
			@ApiResponse(responseCode = "409", description = "Violação de integridade.")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	} 
}
