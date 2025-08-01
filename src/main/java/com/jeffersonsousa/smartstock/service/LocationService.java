package com.jeffersonsousa.smartstock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.CreateStockDTO;
import com.jeffersonsousa.smartstock.dto.LocationResponseDTO;
import com.jeffersonsousa.smartstock.dto.StockCreationMessageDTO;
import com.jeffersonsousa.smartstock.entity.Location;
import com.jeffersonsousa.smartstock.repository.LocationRepository;

@Service
public class LocationService {

	@Autowired
	LocationRepository locationRepository;

	public List<LocationResponseDTO> getAllLocations() {
		List<LocationResponseDTO> list = locationRepository.findAll().stream().map(LocationResponseDTO::new).toList();
		return list;
	}

	public StockCreationMessageDTO createStock(CreateStockDTO dto) {
		
		if(!locationRepository.findAll().isEmpty()) {
			System.out.println("Estoque já foi criado");
		}
		
		List<Location> stock = new ArrayList<>();
		int total = 0;

		for (int i = 0; i < dto.aisle(); i++) {
			char aisleLetter = (char) ('A' + i);

			for (int floor = 1; floor <= dto.floor(); floor++) {

				for (int position = 1; position <= dto.position(); position++) {
					String locationId = generateId(aisleLetter, position, floor);
					
					Location location = instatiateNewLocation(locationId, aisleLetter, position, floor);
					stock.add(location);
					total += 1;
				}
			}
		}

		locationRepository.saveAll(stock);
		
		return new StockCreationMessageDTO("Endereços de estoque criados com sucesso!", total);
	}

	private Location instatiateNewLocation(String locationId, char aisleLetter, int position, int floor) {
		Location location = new Location();
		location.setLocationId(locationId);
		location.setAisle(aisleLetter);
		location.setPosition(position);
		location.setFloor(floor);
		location.setHasProduct(false);
		location.setProduct(null);
		location.setQuantity(0);
		return location;
	}
	
	private String generateId(char aisle, int position, int floor) {
		return aisle + "." + position + "." + floor;
	}

}
