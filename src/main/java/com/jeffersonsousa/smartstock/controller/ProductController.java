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

import com.jeffersonsousa.smartstock.dto.ProductRequestDTO;
import com.jeffersonsousa.smartstock.dto.ProductResponseDTO;
import com.jeffersonsousa.smartstock.dto.ProductUpdateDTO;
import com.jeffersonsousa.smartstock.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductService service;

	@Operation(description = "Cria produto no banco de dados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cria um produto."),
			@ApiResponse(responseCode = "500", description = "Não encontrou a categoria para criar o produto.") // implements 404 not found category
	})
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody ProductRequestDTO dto){
		service.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(description = "Busca produto pelo Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca o produto."),
			@ApiResponse(responseCode = "404", description = "Não encontrou o produto.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
		ProductResponseDTO dto = service.getProductById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@Operation(description = "Busca todos os produtos no banco de dados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca todos os produtos.")
	})
	@GetMapping
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
		List<ProductResponseDTO> list = service.getAllProducts();
		return ResponseEntity.ok().body(list);
	}
	
	@Operation(description = "Busca todos os produtos que estão com o estoque baixo.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca os produtos.")
	})
	@GetMapping(value = "/lowStockProducts")
	public ResponseEntity<List<ProductResponseDTO>> getLowStockProducts(){
		List<ProductResponseDTO> list = service.getLowStockProducts();
		return ResponseEntity.ok().body(list);
	}
	
	@Operation(description = "Edita o produto pelo Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Edita o produto."),
			@ApiResponse(responseCode = "404", description = "Não encontrou o produto.")
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto){
		return ResponseEntity.ok().body(service.update(id,dto));
	}
	
	@Operation(description = "Deleta o produto pelo Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Deleta o produto."),
			@ApiResponse(responseCode = "404", description = "Não encontrou o produto.")
			//não pode deletar um produto vinculado com um endereço ou com historicos de movimentação
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
