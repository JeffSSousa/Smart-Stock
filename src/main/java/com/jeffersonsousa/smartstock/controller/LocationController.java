package com.jeffersonsousa.smartstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonsousa.smartstock.dto.CreateStockDTO;
import com.jeffersonsousa.smartstock.dto.LocationResponseDTO;
import com.jeffersonsousa.smartstock.dto.StockCreationMessageDTO;
import com.jeffersonsousa.smartstock.service.LocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("location")
public class LocationController {

	@Autowired
	private LocationService service;
	
	@Operation(description = "Busca todos os endereços no estoque.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todos os endereços.")
	})
	@GetMapping
	public ResponseEntity<List<LocationResponseDTO>> getAllLocations(){
		List<LocationResponseDTO> list = service.getAllLocations();
		return ResponseEntity.ok().body(list);
	}

	
	@Operation(description = "Gera automaticamente todos os endereços a partir dos dados recebidos da quantidade de ruas,endereços e andares que o estoque terá.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna que o estoque foi criado, informando quantos endereços foram criados."),
			@ApiResponse(responseCode = "400", description = "Regra de estoque violada.")
	})
	@PostMapping
	public ResponseEntity<StockCreationMessageDTO> createStock(@RequestBody CreateStockDTO dto){
		var creation = service.createStock(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creation);
	}
	
}
