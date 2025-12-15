package com.jeffersonsousa.smartstock.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductUpdateDTO(
		
		@Schema(description = "Descrição do produto que deseja editar", example = "TV 40 polegadas")
		String name, 
		
		@Schema(description = "Quantidade minima que o produto terá no estoque antes de alertar que precisa repor.", example = "36")
		Integer minimumQuantity, 
		
		@Schema(description = "Preço unitario do produto.", example = "2999,00")
		Double price) {

}
