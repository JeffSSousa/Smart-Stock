package com.jeffersonsousa.smartstock.dto;

import com.jeffersonsousa.smartstock.entity.Location;

public record LocationResponseDTO(String locationId, 
								  Boolean hasProduct, 
								  String product, 
								  Integer quantity) {

	public LocationResponseDTO(Location location) {
		this(location.getLocationId(), 
			 location.getHasProduct(), 
			 location.getProduct() != null ? location.getProduct().getName() : null,
			 location.getQuantity());
	}
	
}
