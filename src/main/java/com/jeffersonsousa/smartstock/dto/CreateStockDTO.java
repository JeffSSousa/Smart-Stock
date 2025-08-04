package com.jeffersonsousa.smartstock.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateStockDTO(
		
		@Schema(description = "Quantidade de ruas que o estoque terá.", example = "5")
		Integer aisle, 
		
		@Schema(description = "Quantidade de endereços(prateleiras) que o estoque terá.", example = "10")
		Integer position, 
	
		@Schema(description = "Quantidade de andares que o estoque terá.", example = "3")
		Integer floor) {

}
