package com.jeffersonsousa.smartstock.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryRequestDTO(
		
		@Schema(description = "Descrição da categoria do produto", example = "Computadores")
		String name
		
		) {

}
