package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.jeffersonsousa.smartstock.dto.product.ProductRequestDTO;
import com.jeffersonsousa.smartstock.dto.product.ProductResponseDTO;
import com.jeffersonsousa.smartstock.dto.product.ProductUpdateDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.exception.exceptions.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.exceptions.DatabaseException;
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
            Product product = new Product(null,
                                            "Apple Mackbook M1",
                                            null,
                                            10,
                                            6956.0,
                                            category,
                                            null,
                                            null);
			when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
			when(productRepository.save(any(Product.class))).thenReturn(product);
			
			service.create(product, category.getCategoryId());
			
			verify(categoryRepository, times(1)).findById(1L);
			verify(productRepository, times(1)).save(captor.capture());
			
			Product output = captor.getValue();
			
			assertEquals(product.getName(), output.getName());
			assertEquals(product.getMinimumQuantity(), output.getMinimumQuantity());
			assertEquals(product.getPrice(), output.getPrice());
			assertEquals(category.getName(), output.getCategory().getName());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção caso não encontre a categoria.")
		void shouldThrowExceptionWhenCategoryNotFound() {

			Long id = 1L;
            Category category = new Category(1L, "Computer", null);
            Product product = new Product(null,
                    "Apple Mackbook M1",
                    null,
                    10,
                    6956.0,
                    category,
                    null,
                    null);
			when(categoryRepository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.create(product,category.getCategoryId()));
			
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
		@DisplayName("Deve listar Todos os produtos com sucesso.")
		void shouldListAllProducts() {
			
			Category category = new Category(1L, "Computer", null);
			Product product = new Product(1L, "Apple Macbook M1", 0, 10, 6956.0, category, null, null);
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
		
		@Test
		@DisplayName("Deve listar os produtos que estão com o estoque baixo.")
		void shouldListLowStockProducts() {
			
			Category category = new Category(1L, "Computer", null);
			Product macbook = new Product(1L, "Apple Macbook M1", 9, 10, 6956.0, category, null, null);
			Product iphone = new Product(1L, "Apple iPhone 15", 10, 10, 10996.0, category, null, null);
			List<Product> products = List.of(macbook, iphone);
			List<Product> lowStockProducts = products.stream().filter(p -> p.getCurrentQuantity() < p.getMinimumQuantity()).toList();
			when(productRepository.findAll()).thenReturn(products);
			
			List<ProductResponseDTO> output = service.getLowStockProducts();
			
			assertNotNull(output);
			assertEquals(lowStockProducts.size(), output.size());
			assertEquals(lowStockProducts.get(0).getName(), output.get(0).name());
		}
		
		@Test
		@DisplayName("Deve retornar lista vazia quando não há produtos com estoque baixo.")
		void shouldReturnEmptyListWhenNoLowStockProducts() {
		    Category category = new Category(1L, "Computer", null);
		    Product iphone = new Product(1L, "iPhone 15", 20, 10, 10996.0, category, null, null);
		    when(productRepository.findAll()).thenReturn(List.of(iphone));

		    List<ProductResponseDTO> output = service.getLowStockProducts();

		    assertNotNull(output);
		    assertTrue(output.isEmpty());
		}
	}
	
	@Nested
	class update{
		//update
		
		@Test
		@DisplayName("Deve Atualizar dados do produto com sucesso.")
		void shouldUpdateAProduct() {
			Long id = 1L;
			Category category = new Category(id, "Computers", null);
			Product iphone = new Product(1L, "Apple iPhone 15", 10, 10, 10996.0, category, null, null);
			ProductUpdateDTO updateDTO = new ProductUpdateDTO("Apple iPhone 15 Pro Max", 15, 12289.0);
			when(productRepository.findById(id)).thenReturn(Optional.of(iphone));
			
			
			service.update(id, updateDTO);
			
			
			verify(productRepository, times(1)).findById(id);
			verify(productRepository, times(1)).save(captor.capture());
			
			Product output = captor.getValue();
			
			assertEquals(updateDTO.name(), output.getName());
			assertEquals(updateDTO.minimumQuantity(), output.getMinimumQuantity());
			assertEquals(updateDTO.price(), output.getPrice());
		}
		
		
		@Test
		@DisplayName("Deve lançar uma exceção se não encontrar o produto.")
		void shouldThrowExceptionWhenProductNotFound() {
			
			Long id = 1L;
			ProductUpdateDTO updateDTO = new ProductUpdateDTO("Apple iPhone 15 Pro Max", 15, 12289.0);
			when(productRepository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.update(id, updateDTO));
			
			verify(productRepository, times(1)).findById(id);
			assertEquals("Produto com o ID" + id + " não foi encontrado!!", e.getMessage());
		}
	}
	
	@Nested
	class delete{
		
		@Test
		@DisplayName("Deve deletar uma categoria com sucesso.")
		void shouldDeleteAProduct() {
			
			Long id = 1L;
			Category category = new Category(id, "Computers", null);
			Product iphone = new Product(1L, "Apple iPhone 15", 10, 10, 10996.0, category, null, null);
			
			when(productRepository.findById(id)).thenReturn(Optional.of(iphone));
			doNothing().when(productRepository).delete(iphone);
			
			service.delete(id);
			
			verify(productRepository, times(1)).findById(id);
			verify(productRepository, times(1)).delete(iphone);
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção se não encontrar o produto.")
		void shouldThrowExceptionWhenProductNotFound() {
			
			Long id = 1L;
			when(productRepository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.delete(id));
			
			verify(productRepository, times(1)).findById(id);
			assertEquals("Produto com o ID" + id + " não foi encontrado!!", e.getMessage());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção quando o produto estiver vinculado com outras entidades.")
		void shouldThrowExceptionWhenProductIsLinkedToOtherEntities() {
			
			Long id = 1L;
			Category category = new Category(id, "Computers", null);
			Product iphone = new Product(1L, "Apple iPhone 15", 10, 10, 10996.0, category, null, null);
			
			when(productRepository.findById(id)).thenReturn(Optional.of(iphone));
			doThrow(DataIntegrityViolationException.class).when(productRepository).delete(iphone);
			
			DatabaseException e = assertThrows(DatabaseException.class, () -> service.delete(id));
			
			assertEquals("Não é possível excluir o produto. Ele está vinculado a outras entidades no sistema.", e.getMessage());		
		}
	
	}
}
