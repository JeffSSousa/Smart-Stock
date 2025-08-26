package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.jeffersonsousa.smartstock.dto.ProductRequestDTO;
import com.jeffersonsousa.smartstock.dto.ProductResponseDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.exception.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.repository.CategoryRepository;
import com.jeffersonsousa.smartstock.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private ProductService service;
	
	@Captor
	private ArgumentCaptor<Product> captor;
	
	@Nested
	class create{
		
		@Test
		@DisplayName("Deve criar um produto com sucesso no banco de dados.")
		void shouldACreateProduct() {
			
			Category category = new Category(1L, "Computer", null);
			ProductRequestDTO dto = new ProductRequestDTO("Apple Mackbook M1", 10, 6956.0, category.getCategoryId());
			Product product = new Product(dto, category);
			when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
			when(productRepository.save(any(Product.class))).thenReturn(product);
			
			service.create(dto);
			
			verify(categoryRepository, times(1)).findById(1L);
			verify(productRepository, times(1)).save(captor.capture());
			
			Product output = captor.getValue();
			
			assertEquals(dto.name(), output.getName());
			assertEquals(dto.minimumQuantity(), output.getMinimumQuantity());
			assertEquals(dto.price(), output.getPrice());
			assertEquals(category.getName(), output.getCategory().getName());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção caso não encontre a categoria.")
		void shouldThrowExceptionWhenCategoryNotFound() {

			Long id = 1L;
			ProductRequestDTO dto = new ProductRequestDTO("Apple Macbook M1", 10, 6956.0, id);
			when(categoryRepository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.create(dto));
			
			assertEquals("Não foi encontrada uma categoria com o ID: " + id, e.getMessage());
			verify(categoryRepository, times(1)).findById(id);
		}
	}
	
	@Nested
	class getProductById{
		
		@Test
		@DisplayName("Deve buscar um produto pelo ID com sucesso.")
		void shouldFindAProductById() {
			
			Long id = 1L;
			Category category = new Category(id, "Computer", null);
			ProductRequestDTO dto = new ProductRequestDTO("Apple Mackbook M1", 10, 6956.0, category.getCategoryId());
			Product product = new Product(dto, category);
			
			when(productRepository.findById(id)).thenReturn(Optional.of(product));
			
			ProductResponseDTO output = service.getProductById(id);
			
			verify(productRepository, times(1)).findById(id);
			
			assertNotNull(output);
			assertEquals(dto.name(), output.name());
			assertEquals(0, output.currentQuantity());
			assertEquals(dto.minimumQuantity(), output.minimumQuantity());
			assertEquals(dto.price(), output.price());
			assertEquals(category.getName(), output.Category());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção se não encontrar o produto.")
		void shouldThrowExceptionWhenProductNotFound() {
			
			Long id = 1L;
			when(productRepository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.getProductById(id));
			
			verify(productRepository, times(1)).findById(id);
			assertEquals("Produto com o ID" + id + " não foi encontrado!!", e.getMessage());
		}
	}
	
	@Nested
	class getAllProduct{
		
		@Test
		@DisplayName("Deve listar Todos os produtos com sucesso")
		void shouldListAllProducts() {
			
			Category category = new Category(1L, "Computer", null);
			Product product = new Product(1L, "Apple Macbook", 0, 10, 6956.0, category, null, null);
			List<Product> products = List.of(product);
			when(productRepository.findAll()).thenReturn(products);
			
			List<ProductResponseDTO> output = service.getAllProducts();
			
			assertNotNull(output);
			assertEquals(products.size(), output.size());
			assertEquals(products.get(0).getName(), output.get(0).name());
		}
	}
	
	@Nested
	class getLowStockProducts{
		//listar
	}
	
	@Nested
	class update{
		//update
		//não encontrado
	}
	
	@Nested
	class updateDate{
		//suceess
	}
	
	@Nested
	class delete{
		//delete
		//not found
		//integrityViolation
	}
}
