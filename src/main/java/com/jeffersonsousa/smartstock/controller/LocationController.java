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

@RestController
@RequestMapping("location")
public class LocationController {

	@Autowired
	private LocationService service;
	
	@GetMapping
	public ResponseEntity<List<LocationResponseDTO>> getAllLocations(){
		List<LocationResponseDTO> list = service.getAllLocations();
		return ResponseEntity.ok().body(list);
	}

	
	@PostMapping
	public ResponseEntity<StockCreationMessageDTO> createStock(@RequestBody CreateStockDTO dto){
		var creation = service.createStock(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creation);
	}
	
}
