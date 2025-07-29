package com.jeffersonsousa.smartstock.dto;

import com.jeffersonsousa.smartstock.entity.Product;

public record ProductResponseDTO(Long productId, 
								 String name, 
								 Integer currentQuantity, 
								 Integer minimumQuantity, 
								 Double price,
								 String Category) {

	public ProductResponseDTO(Product product) {
		this(product.getProductId(),
			 product.getName(),
			 product.getCurrentQuantity(),
			 product.getMinimumQuantity(),
			 product.getPrice(),
			 product.getCategory().getName());
	}
}
