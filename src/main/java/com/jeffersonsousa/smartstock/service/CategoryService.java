package com.jeffersonsousa.smartstock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.CategoryRequestDTO;
import com.jeffersonsousa.smartstock.dto.CategoryResponseDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public void createCategory(CategoryRequestDTO dto) {
		Category category = new Category(dto);
		categoryRepository.save(category);
	}

	public CategoryResponseDTO findById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		return new CategoryResponseDTO(category.get());
	}

	public List<CategoryResponseDTO> getAllCategories() {
		List<CategoryResponseDTO> list = categoryRepository.findAll().stream().map(CategoryResponseDTO::new).toList();
		return list;
	}

	public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
		Category entity = categoryRepository.getReferenceById(id);
		entity.setName(dto.name());
		categoryRepository.save(entity);
		return new CategoryResponseDTO(entity);
	}

	public void delete(Long id) {
		categoryRepository.deleteById(id);
	}
}
