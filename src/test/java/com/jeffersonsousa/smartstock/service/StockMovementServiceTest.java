package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.jeffersonsousa.smartstock.dto.MovementRequestDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Location;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.entity.StockMovement;
import com.jeffersonsousa.smartstock.exception.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.StockException;
import com.jeffersonsousa.smartstock.repository.LocationRepository;
import com.jeffersonsousa.smartstock.repository.ProductRepository;
import com.jeffersonsousa.smartstock.repository.StockMovementRepository;

@ExtendWith(MockitoExtension.class)
public class StockMovementServiceTest {

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private LocationRepository locationRepository;
	
	@Mock
	private StockMovementRepository movementRepository;
	
	@InjectMocks
	private StockMovementService service;
	
	@Captor
	private ArgumentCaptor<Location> captorLocation;
	
	@Captor
	private ArgumentCaptor<StockMovement> captorStockMovement;
	
	@Nested
	class input{
		//input success
		
		@Test
		@DisplayName("Deve fazer a entrada de um produto no estoque e adicionar a movimentação no estoque.")
		void shouldInsertProductsIntoStockAndAddMovementHistory() {
			
			Category category = new Category(1L, "Computer", null);
			Product product = new Product(1L, "Apple Macbook M1", 0, 10, 6956.0, category, null, null);
			MovementRequestDTO dto = new MovementRequestDTO(10, product.getProductId());
			Location location = new Location("A.1.1", 'A', 1, 1, false, null, null, null);
			
			when(productRepository.findById(1L)).thenReturn(Optional.of(product));
			when(locationRepository.findFirstByHasProductFalse()).thenReturn(Optional.of(location));
			
			service.Input(dto);
			
			verify(productRepository, times(1)).findById(any());
			verify(locationRepository, times(1)).findFirstByHasProductFalse();
			verify(locationRepository, times(1)).save(captorLocation.capture());
			verify(movementRepository, times(1)).save(captorStockMovement.capture());
			
			Location outputLocation = captorLocation.getValue();
			StockMovement outputStockMovement = captorStockMovement.getValue();
			
			
			assertTrue(outputLocation.getHasProduct());
			assertEquals(product.getName(), outputLocation.getProduct().getName());
			assertEquals(dto.quantity(), outputLocation.getQuantity());
			
			assertEquals(product.getName(), outputStockMovement.getProduct().getName());
			assertEquals(location.getLocationId(), outputStockMovement.getLocation().getLocationId());
			assertEquals(dto.quantity(), outputStockMovement.getQuantity());
			assertEquals("Entrada", outputStockMovement.getType());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção se a quantidade de produtos for menor ou igual a 0")
		void shouldThrowExceptionWhenQuantityIsLessThanOrEqualToZero() {
			
			MovementRequestDTO dto = new MovementRequestDTO(0, 1L);
			
			StockException e = assertThrows(StockException.class,()-> service.Input(dto));
			
			assertEquals("A quantidade de entrada deve ser maior que zero!!", e.getMessage());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção quando o produto não for encontrado.")
		void shouldThrowExceptionWhenProductNotFound() {
			
			MovementRequestDTO dto = new MovementRequestDTO(10, 1L);
			when(productRepository.findById(dto.productId())).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.Input(dto));
			
			assertEquals("Produto com o ID " + dto.productId() + " não foi encontrado!!", e.getMessage());
			verify(productRepository, times(1)).findById(any());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção se não for encontrado nenhuma endereço livre no estoque")
		void shouldThrowExceptionIfItDoesNotFindAFreeAddressInStock() {
			
			MovementRequestDTO dto = new MovementRequestDTO(10, 1L);
			Category category = new Category(1L, "Computer", null);
			Product product = new Product(1L, "iPhone 15 Pro Max", 0, 10, 12003.2, category, null, null);
			
			when(productRepository.findById(dto.productId())).thenReturn(Optional.of(product));
			when(locationRepository.findFirstByHasProductFalse()).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.Input(dto));
			
			assertEquals("Não foi encontrado Endereço livre!!", e.getMessage());
			verify(productRepository, times(1)).findById(any());
			verify(locationRepository, times(1)).findFirstByHasProductFalse();
			
		}
	}
	
	@Nested
	class output{
		//output success
		//não foi encontrado produto
		//saldo insufiente no estoque
		// quantiti deve ser maior que 0
	}
	
}
