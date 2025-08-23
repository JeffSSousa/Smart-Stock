package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.jeffersonsousa.smartstock.dto.CategoryRequestDTO;
import com.jeffersonsousa.smartstock.dto.CategoryResponseDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.exception.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.DatabaseException;
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
	
	@Nested
	class findById{
		
		@Test
		@DisplayName("Deve procurar uma categoria pelo ID")
		void shouldFindACategoryById() {
			
			Long id = 1L;
			String name = "Computer";
			Category category = new Category(id, name, null);
			
			when(repository.findById(id)).thenReturn(Optional.of(category));
			
			CategoryResponseDTO output = service.findById(id);
			
			assertNotNull(output);
			assertEquals(id, output.id());
			assertEquals(name, output.name());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção quando a categoria não for encontrada")
		void shouldThrowExceptionWhenCategoryNotFound() {
			
			Long id = 1L;
			when(repository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.findById(id));
			assertEquals("Não foi encontrada uma categoria com o ID: " + id, e.getMessage());
			
			verify(repository, times(1)).findById(id);
		}
		
	}
	
	@Nested
	class getAllCategories{
		
		@Test
		@DisplayName("Deve listar todas as categorias com sucesso")
		void shouldListAllCategories() {
			
			Category category = new Category(1L, "Computer", null);
			List<Category> categories = List.of(category);
			
			when(repository.findAll()).thenReturn(categories);
			
			List<CategoryResponseDTO> output = service.getAllCategories();
			
			verify(repository, times(1)).findAll();
			assertNotNull(output);
			assertEquals(categories.size(), output.size());
			assertEquals(categories.get(0).getCategoryId(), output.get(0).id());
			assertEquals(categories.get(0).getName(), output.get(0).name());
		}
		
	}
	
	@Nested
	class delete{
		
		@Test
		@DisplayName("Deve deletar uma categoria com sucesso")
		void shouldDeleteACategory() {
			
			Long id = 1L;
			Category category = new Category(id, "Computer", null);
			
			when(repository.findById(id)).thenReturn(Optional.of(category));
			doNothing().when(repository).delete(category);
			
			service.delete(id);
			
			verify(repository, times(1)).findById(id);
			verify(repository, times(1)).delete(category);
		}
		
		
		@Test
		@DisplayName("Deve lançar uma exceção quando a categoria não for encontrada")
		void shouldThrowExceptionWhenCategoryNotFound() {
			
			Long id = 1L;
			when(repository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.delete(id));
			
			assertEquals("Não foi encontrada uma categoria com o ID: " + id, e.getMessage());
			verify(repository, times(1)).findById(id);
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção quando a categoria estiver vinculada com outras entidades")
		void shouldThrowExceptionWhenCategoryIsLinkedToOtherEntities() {
			
			Long id = 1L;
			Category category = new Category(id, "Computer", null);
			
			when(repository.findById(id)).thenReturn(Optional.of(category));
			doThrow(DataIntegrityViolationException.class).when(repository).delete(category);
			
			DatabaseException e = assertThrows(DatabaseException.class, () -> service.delete(id));
			
			assertEquals("Não é possível excluir a categoria. Ele está vinculado a outras entidades no sistema.", e.getMessage());
		}
	}
	
	@Nested
	class update{
		
		
		@Test
		@DisplayName("Deve atualizar dados da categoria com sucesso")
		void shouldUpdateACategory(){
			
			Long id = 1L;
			Category category = new Category(id, "Computers", null);
			CategoryRequestDTO dto = new CategoryRequestDTO("Computer");
			when(repository.findById(id)).thenReturn(Optional.of(category));
			
			CategoryResponseDTO output = service.update(id, dto);
			
			verify(repository, times(1)).save(category);
			assertEquals(dto.name(), output.name());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção quando a categoria não for encontrada")
		void shouldThrowExceptionWhenCategoryNotFound() {
			
			Long id = 1L;
			CategoryRequestDTO dto = new CategoryRequestDTO("Computer");
			when(repository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.update(id, dto));
			
			assertEquals("Não foi encontrada uma categoria com o ID: " + id, e.getMessage());
			verify(repository, times(1)).findById(id);
		}
		
	}
}
