package com.jeffersonsousa.smartstock.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;

public record LocationResponseDTO(
		
		@Schema(description = "Identificador unico do endereço onde o produto está armazenado", example = "A.1.2")
		String locationId, 
		
		@Schema(description = "Valida de tem produto no endereço", example = "True")
		Boolean hasProduct, 
		
		@Schema(description = "Descrição do produto que está armazenado nesse endereço", example = "TV 50 Polegadas 4k SmartTV")
		Long productId,
		
		@Schema(description = "Quantidade de produtos nesse endereço", example = "10")						  
		Integer quantity
		
		){
	
}
