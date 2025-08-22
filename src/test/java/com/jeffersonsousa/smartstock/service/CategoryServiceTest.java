package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jeffersonsousa.smartstock.dto.CategoryRequestDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepository repository;
	
	@InjectMocks
	private CategoryService service;
	
	@Captor
	private ArgumentCaptor<Category> categoryCaptor;
	
	@Nested
	class insert {
		
		@Test
		@DisplayName("Deve inserir uma categoria com sucesso")
		void shouldCreateACategory() {
			
			String name = "Computer";
			CategoryRequestDTO dto = new CategoryRequestDTO(name);
			Category category = new Category(dto);
			
			when(repository.save(any(Category.class))).thenReturn(category);
			
			service.createCategory(dto);
			
			verify(repository, times(1)).save(categoryCaptor.capture());
			Category captured = categoryCaptor.getValue();
			
			assertEquals(name, captured.getName());
			
		}	
	}
	
	
}
