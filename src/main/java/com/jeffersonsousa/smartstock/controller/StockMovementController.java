package com.jeffersonsousa.smartstock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonsousa.smartstock.dto.MovementRequestDTO;
import com.jeffersonsousa.smartstock.service.StockMovementService;

@RestController
@RequestMapping("stockmovements")
public class StockMovementController {
	
	@Autowired
	private StockMovementService service;
	
	@PostMapping(value = "/input")
	public ResponseEntity<Void> productInput(@RequestBody MovementRequestDTO dto){
		service.Input(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping(value = "/output")
	public ResponseEntity<Void> productOutput(@RequestBody MovementRequestDTO dto){
		service.Output(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	
}
