package com.jeffersonsousa.smartstock.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.MovementRequestDTO;
import com.jeffersonsousa.smartstock.entity.Location;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.entity.StockMovement;
import com.jeffersonsousa.smartstock.exception.exceptions.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.exceptions.InsufficientStockException;
import com.jeffersonsousa.smartstock.exception.exceptions.StockException;
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
		
		if(dto.quantity() <= 0) {
			throw new StockException("A quantidade de entrada deve ser maior que zero!!");
		}
		
		Product product = productRepository.findById(dto.productId())
				.orElseThrow(() -> new ControllerNotFoundException("Produto com o ID " + dto.productId() + " não foi encontrado!!"));

		Location location = locationRepository.findFirstByHasProductFalse()
				.orElseThrow(() -> new  ControllerNotFoundException("Não foi encontrado Endereço livre!!"));

		location.setProduct(product);
		location.setQuantity(dto.quantity());
		location.setHasProduct(true);

		StockMovement newInput = new StockMovement(dto);
		newInput.setType("Entrada");
		newInput.setLocation(location);
		newInput.setProduct(product);

		locationRepository.save(location);
		movementRepository.save(newInput);

		updateCurrentQuantity(product);
	}

	public void Output(MovementRequestDTO dto) {

		Product product = productRepository.findById(dto.productId())
				.orElseThrow(() -> new ControllerNotFoundException("Produto com o ID " + dto.productId() + " não foi encontrado!!"));

		if (dto.quantity() > product.getCurrentQuantity()) {
			throw new InsufficientStockException("Saldo Insuficiente no Estoque!!");
		}
		
		if(dto.quantity() <= 0) {
			throw new StockException("A quantidade de saída deve ser maior que zero!!");
		}

		List<Location> list = locationRepository.findByProduct(product);
		int totalQuantity = dto.quantity();
		
		List<Location> updateLocations = new ArrayList<>();
		List<StockMovement> newStockMovements = new ArrayList<>();

		for (Location location : list) {
			if (totalQuantity == 0) break;

	        int available = location.getQuantity();
	        int toWithdraw = Math.min(available, totalQuantity);

	        location.setQuantity(available - toWithdraw);

	        StockMovement newOutput = new StockMovement();
	        newOutput.setType("Saída");
	        newOutput.setQuantity(toWithdraw);
	        newOutput.setMovimentationDate(Instant.now());
	        newOutput.setProduct(product);
	        newOutput.setLocation(location);
			

			resetLocation(location);
			updateLocations.add(location);
			newStockMovements.add(newOutput);
			totalQuantity -= toWithdraw;
		}

		updateCurrentQuantity(product);
		locationRepository.saveAll(updateLocations);
		movementRepository.saveAll(newStockMovements);
		
	}

	private void updateCurrentQuantity(Product product) {
		int quantity = 0;

		List<Location> list = locationRepository.findByProduct(product);

		for (Location location : list) {
			quantity += location.getQuantity();
		}

		product.setCurrentQuantity(quantity);
		productRepository.save(product);
	}

	private void resetLocation(Location location) {
		if(location.getQuantity() == 0) {
		   location.setProduct(null);
		   location.setHasProduct(false);
		}
	}
}
