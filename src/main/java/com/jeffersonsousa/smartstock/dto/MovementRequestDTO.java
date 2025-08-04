package com.jeffersonsousa.smartstock.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MovementRequestDTO (
		
		@Schema(description = "Quantidade de produtos que deseja movimentar", example = "36")
		Integer quantity,
									  
		@Schema(description = "Id do produto que deseja movimentar", example = "1")
		Long productId){

}
