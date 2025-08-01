package com.jeffersonsousa.smartstock.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.ProductInputRequestDTO;
import com.jeffersonsousa.smartstock.entity.Location;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.entity.StockMovement;
import com.jeffersonsousa.smartstock.repository.LocationRepository;
import com.jeffersonsousa.smartstock.repository.ProductRepository;
import com.jeffersonsousa.smartstock.repository.StockMovementRepository;

@Service
public class StockMovementService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private StockMovementRepository movementRepository;
	
	public void Input(ProductInputRequestDTO dto) {
		
		Product product = productRepository.getReferenceById(dto.productId());
		
		Optional<Location> location = locationRepository.findFirstByHasProductFalse();
		
		location.get().setProduct(product);
		location.get().setQuantity(dto.quantity());
		location.get().setHasProduct(true);
		
		StockMovement newInput = new StockMovement(dto);
		newInput.setType("Entrada");
		newInput.setLocation(location.get());
		
		locationRepository.save(location.get());
		movementRepository.save(newInput);
	}
}
