package com.jeffersonsousa.smartstock.dto.product;

import com.jeffersonsousa.smartstock.entity.Product;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductResponseDTO(
		
		@Schema(description = "Identificador unico localizar o produto", example = "1")
		Long productId, 
								 
		@Schema(description = "Descrição do nome do produto", example = "Notebook 3.2 ghz 16gb ram")
		String name, 
								 
		@Schema(description = "Quantidade atual em estoque", example = "36")
		Integer currentQuantity, 
								 
		@Schema(description = "Quantidade minima de produtos para validar se o estoque está com poucos produtos", example = "10")
		Integer minimumQuantity, 
								 
		@Schema(description = "Valor unitario do produto", example = "2599,63")
		Double price,
								 
		@Schema(description = "Categoria que o produto se encaixa", example = "Computadores")
		String category
		) {

	public ProductResponseDTO(Product product) {
		this(product.getProductId(),
			 product.getName(),
			 product.getCurrentQuantity(),
			 product.getMinimumQuantity(),
			 product.getPrice(),
			 product.getCategory().getName());
	}
}
