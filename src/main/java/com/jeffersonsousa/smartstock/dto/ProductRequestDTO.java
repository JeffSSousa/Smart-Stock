package com.jeffersonsousa.smartstock.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductRequestDTO(
		
		@Schema(description = "Descrição do produto.", example = "TV 50 polegadas 4k Smart")
		String name, 
		
		@Schema(description = "Quantidade minima que o produto terá no estoque antes de alertar que precisa repor.", example = "36")
		Integer minimumQuantity, 
		
		@Schema(description = "Preço unitario do produto.", example = "2999,00")
		Double price, 
		
		@Schema(description = "Id da categoria que o produto se encaixa.", example = "36")
		Long categoryId) {

}
