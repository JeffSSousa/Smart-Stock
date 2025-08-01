package com.jeffersonsousa.smartstock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonsousa.smartstock.dto.ProductInputRequestDTO;
import com.jeffersonsousa.smartstock.service.StockMovementService;

@RestController
@RequestMapping("stockmovements")
public class StockMovementController {
	
	@Autowired
	private StockMovementService service;
	
	@PostMapping(value = "/input")
	public ResponseEntity<Void> productInput(@RequestBody ProductInputRequestDTO dto){
		service.Input(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
