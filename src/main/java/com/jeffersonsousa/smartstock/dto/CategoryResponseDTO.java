package com.jeffersonsousa.smartstock.dto;

import com.jeffersonsousa.smartstock.entity.Category;

public record CategoryResponseDTO(Long id, String name) {

	public CategoryResponseDTO(Category category) {
		this(category.getCategoryId(), category.getName());
	}
	
}
