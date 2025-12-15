package com.jeffersonsousa.smartstock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.exception.exceptions.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.exceptions.DatabaseException;
import com.jeffersonsousa.smartstock.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Não foi encontrada uma categoria com o ID: " + id));
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category update(Long id, Category category) {
		Category entity = categoryRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Não foi encontrada uma categoria com o ID: " + id));
		
		entity.setName(category.getName());
		categoryRepository.save(entity);
		
		return entity;
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
