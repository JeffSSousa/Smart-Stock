package com.jeffersonsousa.smartstock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.category.CategoryRequestDTO;
import com.jeffersonsousa.smartstock.dto.category.CategoryResponseDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.exception.exceptions.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.exceptions.DatabaseException;
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
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Não foi encontrada uma categoria com o ID: " + id));
		
		return new CategoryResponseDTO(category);
	}

	public List<CategoryResponseDTO> getAllCategories() {
		List<CategoryResponseDTO> list = categoryRepository.findAll().stream().map(CategoryResponseDTO::new).toList();
		return list;
	}

	public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
		Category entity = categoryRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Não foi encontrada uma categoria com o ID: " + id));
		
		entity.setName(dto.name());
		categoryRepository.save(entity);
		
		return new CategoryResponseDTO(entity);
	}

	public void delete(Long id) {
		
		try {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Não foi encontrada uma categoria com o ID: " + id));
		
		categoryRepository.delete(category);
		
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Não é possível excluir a categoria. Ele está vinculado a outras entidades no sistema.");
		}
	}
}
