package com.jeffersonsousa.smartstock.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryRequestDTO(
		
		@Schema(description = "Descrição da categoria do produto", example = "Computadores")
		String name
		
		) {

}
