package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
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

import com.jeffersonsousa.smartstock.dto.stockmovement.MovementRequestDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Location;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.entity.StockMovement;
import com.jeffersonsousa.smartstock.exception.exceptions.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.exceptions.InsufficientStockException;
import com.jeffersonsousa.smartstock.exception.exceptions.StockException;
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
	
	@Captor
	private ArgumentCaptor<List<Location>> captorStockLocation;
	
	@Captor
	private ArgumentCaptor<List<StockMovement>> captorNewStockMovements;
	
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
			
			Long id = 1L;
			MovementRequestDTO dto = new MovementRequestDTO(10, id);
			when(productRepository.findById(id)).thenReturn(Optional.empty());
			
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
		
		@Test
		@DisplayName("Deve realizar a saída de um produto do estoque atualizando localizações e registrando movimentações.")
		void shouldRemoveProductsFromStock() {
			
			MovementRequestDTO dto = new MovementRequestDTO(10, 1L);
			Category category = new Category(1L, "Computer", null);
			Product product = new Product(1L, "iPhone 15", 15, 10, 4999.9, category, null, null);
			Location location = new Location("A.1.1", 'A', 1, 1, true, product, 9, null);
			Location location2 = new Location("A.1.2", 'A', 1, 2, true, product, 6, null);
			List<Location> stock = List.of(location,location2);
			
			when(productRepository.findById(dto.productId())).thenReturn(Optional.of(product));
			when(locationRepository.findByProduct(product)).thenReturn(stock);
			
			service.Output(dto);
			
			verify(productRepository, times(1)).findById(any());
			verify(locationRepository, atLeastOnce()).findByProduct(any(Product.class));
			verify(locationRepository, times(1)).saveAll(captorStockLocation.capture());
			verify(movementRepository, times(1)).saveAll(captorNewStockMovements.capture());
		
			List<Location> locationsOutput = captorStockLocation.getValue();
			assertEquals(0, locationsOutput.get(0).getQuantity());
			assertFalse(locationsOutput.get(0).getHasProduct());
			assertEquals(5, locationsOutput.get(1).getQuantity());
			assertTrue(locationsOutput.get(1).getHasProduct());
			
			List<StockMovement> stockMovementsOutput = captorNewStockMovements.getValue();
			assertEquals(2, stockMovementsOutput.size());
			assertEquals(9, stockMovementsOutput.get(0).getQuantity());
			assertEquals(1, stockMovementsOutput.get(1).getQuantity());	
			assertEquals("Saída", stockMovementsOutput.get(0).getType());
			assertNotNull(stockMovementsOutput.get(0).getMovimentationDate());
			assertEquals(product.getName(), stockMovementsOutput.get(0).getProduct().getName());
			assertEquals(location.getLocationId(), stockMovementsOutput.get(0).getLocation().getLocationId());
			
			assertEquals(5, product.getCurrentQuantity());
		}
		
		@Test
		@DisplayName("Deve lançar exceção quando o produto não for encontrado.")
		void shouldThrowExceptionWhenProductNotFound() {
			
			Long id = 1L;
			MovementRequestDTO dto = new MovementRequestDTO(10, id);
			
			when(productRepository.findById(id)).thenReturn(Optional.empty());
			
			ControllerNotFoundException e = assertThrows(ControllerNotFoundException.class, () -> service.Output(dto));
			
			assertEquals("Produto com o ID " + dto.productId() + " não foi encontrado!!", e.getMessage());	
		}
		
		
		@Test
		@DisplayName("Deve lançar exceção quando o saldo for insulficiente no estoque.")
		void shoulThrowExceptionWhenTheBalanceIsInsufficientInStock() {
			
			Long id = 1L;
			MovementRequestDTO dto = new MovementRequestDTO(10, 1L);
			Category category = new Category(1L, "Computer", null);
			Product product  = new Product(1L, "iPhone 15 Pro Max", 9, 10, 12266.2, category, null, null);
			
			when(productRepository.findById(id)).thenReturn(Optional.of(product));
			
			InsufficientStockException e = assertThrows(InsufficientStockException.class, () -> service.Output(dto));
			
			assertEquals("Saldo Insuficiente no Estoque!!", e.getMessage());
		}
		
		@Test
		@DisplayName("Deve lançar exceção quando a quantidade for menor ou igual a zero.")
		void shouldThrowExceptionWhenQuantityIsLessThanOrEqualToZero() {
			
			Long id = 1L;
			MovementRequestDTO dto = new MovementRequestDTO(0, 1L);
			Category category = new Category(1L, "Computer", null);
			Product product  = new Product(1L, "iPhone 15 Pro Max", 9, 10, 12266.2, category, null, null);
			
			when(productRepository.findById(id)).thenReturn(Optional.of(product));
		
			StockException e = assertThrows(StockException.class, () -> service.Output(dto));
			
			assertEquals("A quantidade de saída deve ser maior que zero!!", e.getMessage());
		}
	}
	
}
