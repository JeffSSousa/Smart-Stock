package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jeffersonsousa.smartstock.dto.stockmovement.CreateStockDTO;
import com.jeffersonsousa.smartstock.dto.location.LocationResponseDTO;
import com.jeffersonsousa.smartstock.dto.info.StockCreationMessageDTO;
import com.jeffersonsousa.smartstock.entity.Location;
import com.jeffersonsousa.smartstock.exception.exceptions.StockException;
import com.jeffersonsousa.smartstock.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

	@Mock
	private LocationRepository repository;
	
	@InjectMocks
	private LocationService service;
	
	@Captor
	private ArgumentCaptor<List<Location>> captor;
	
	@Nested
	class getAllLocations{
		
		@Test
		@DisplayName("Deve listar todos as localizações(endereços) do estoque.")
		void shouldListAllLocations() {
			
			Location location1 = new Location("A.1.1", 'A', 1, 1, false, null, null, null);
			Location location2 = new Location("A.2.1", 'A', 2, 1, false, null, null, null);
			List<Location> stock = List.of(location1,location2);
			
			when(repository.findAll()).thenReturn(stock);
			
			List<Location> output = service.getAllLocations();
			
			verify(repository, times(1)).findAll();
			
			assertNotNull(output);
			assertEquals(stock.size(), output.size());
			assertEquals(stock.get(0).getLocationId(), output.get(0).getLocationId());
            assertEquals(stock.get(1).getLocationId(), output.get(1).getLocationId());
		}
	}
	
	@Nested
	class createStock{
		
		@Test
		@DisplayName("Deve criar todas as localizações do estoque com sucesso.")
		void shouldCreateAllLocationsInStock() {
			

			Location location1 = new Location("A.1.1", 'A', 1, 1, false, null, null, null);
			Location location2 = new Location("A.2.1", 'A', 2, 1, false, null, null, null);
			List<Location> stock = List.of(location1,location2);
			CreateStockDTO dto = new CreateStockDTO(1, 2, 1);
			
			when(repository.saveAll(anyList())).thenReturn(stock);
			
			StockCreationMessageDTO output = service.createStock(dto);
			
			verify(repository, times(1)).saveAll(captor.capture());
			List<Location> savedLocations = captor.getValue();
			
			
			assertNotNull(output);
			assertEquals("Endereços de estoque criados com sucesso!", output.msg());
			assertEquals(stock.size(), output.total());
			assertEquals("A.1.1", savedLocations.get(0).getLocationId());
			assertEquals("A.2.1", savedLocations.get(1).getLocationId());
			
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção caso o estoque já tenha sido criado.")
		void shouldThrowExceptionIfStockHasAlreadyBeenCreated() {
			
			Location location1 = new Location("A.1.1", 'A', 1, 1, false, null, null, null);
			Location location2 = new Location("A.2.1", 'A', 2, 1, false, null, null, null);
			List<Location> stock = List.of(location1,location2);
			CreateStockDTO dto = new CreateStockDTO(1, 2, 1);
			
			when(repository.findAll()).thenReturn(stock);
			
			StockException e = assertThrows(StockException.class, () -> service.createStock(dto));
			
			assertEquals("Estoque já foi criado!", e.getMessage());
			
		}
	}
	
}
