package com.jeffersonsousa.smartstock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.MovementRequestDTO;
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
	
	public void Input(MovementRequestDTO dto) {
		
		Product product = productRepository.getReferenceById(dto.productId());
		
		Optional<Location> location = locationRepository.findFirstByHasProductFalse();
		
		location.get().setProduct(product);
		location.get().setQuantity(dto.quantity());
		location.get().setHasProduct(true);
		
		StockMovement newInput = new StockMovement(dto);
		newInput.setType("Entrada");
		newInput.setLocation(location.get());
		newInput.setProduct(product);
		
		locationRepository.save(location.get());
		movementRepository.save(newInput);
		
		updateCurrentQuantity(product);
	}
	
	
	private void updateCurrentQuantity(Product product) {
		int quantity = 0 ;
		
		List<Location> list = locationRepository.findByProduct(product);
		
		for(Location location: list) {
			quantity += location.getQuantity();
		}
		
		product.setCurrentQuantity(quantity);
		productRepository.save(product);
	}
}
