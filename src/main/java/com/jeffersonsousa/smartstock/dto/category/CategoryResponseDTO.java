package com.jeffersonsousa.smartstock.dto.category;

import com.jeffersonsousa.smartstock.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryResponseDTO(
		
		
		@Schema(description = "Identificador unico localizar a categoria", example = "2")
		Long id, 
		
		@Schema(description = "Descrição da categoria", example = "Eletronicos")
		String name) {

	public CategoryResponseDTO(Category category) {
		this(category.getCategoryId(), category.getName());
	}
	
}
