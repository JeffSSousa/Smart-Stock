package com.jeffersonsousa.smartstock.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record StockCreationMessageDTO(
		
		@Schema(description = "Mensagem que retornará após criar o estoque com sucesso", example = "Endereços de estoque criados com sucesso!")
		String msg, 
		
		@Schema(description = "Total de endereços criados no banco de dados", example = "100")
		Integer total) {

}
