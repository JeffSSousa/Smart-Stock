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

@RestController
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductService service;

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody ProductRequestDTO dto){
		service.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
		ProductResponseDTO dto = service.getProductById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
		List<ProductResponseDTO> list = service.getAllProducts();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/lowStockProducts")
	public ResponseEntity<List<ProductResponseDTO>> getLowStockProducts(){
		List<ProductResponseDTO> list = service.getLowStockProducts();
		return ResponseEntity.ok().body(list);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto){
		return ResponseEntity.ok().body(service.update(id,dto));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
