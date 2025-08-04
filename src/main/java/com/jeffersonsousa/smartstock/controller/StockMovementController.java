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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("stockmovements")
public class StockMovementController {
	
	@Autowired
	private StockMovementService service;
	
	@Operation(description = "Realiza a entrada de produtos no estoque e cria historico da movimentação.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Realiza a entrada dos produtos e cria movimentação de estoque."),
			@ApiResponse(responseCode = "404", description = "Não encontrou o produto ou endereço livre."),
			@ApiResponse(responseCode = "400", description = "Regra de Estoque violada.")
	})
	@PostMapping(value = "/input")
	public ResponseEntity<Void> productInput(@RequestBody MovementRequestDTO dto){
		service.Input(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(description = "Realiza a saída de produtos no estoque e cria historico da movimentação.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Realiza a saída dos produtos e cria movimentação de estoque."),
			@ApiResponse(responseCode = "404", description = "Não encontrou o produto."),
			@ApiResponse(responseCode = "400", description = "Saída negada.")
	})
	@PostMapping(value = "/output")
	public ResponseEntity<Void> productOutput(@RequestBody MovementRequestDTO dto){
		service.Output(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	
}
