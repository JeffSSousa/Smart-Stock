package com.jeffersonsousa.smartstock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jeffersonsousa.smartstock.dto.LocationResponseDTO;
import com.jeffersonsousa.smartstock.entity.Location;
import com.jeffersonsousa.smartstock.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

	@Mock
	private LocationRepository repository;
	
	@InjectMocks
	private LocationService service;
	
	@Nested
	class getAllLocations{
		
		@Test
		@DisplayName("Deve listar todos as localizações(endereços) do estoque.")
		void shouldListAllLocations() {
			
			Location location1 = new Location("A.1.1", 'A', 1, 1, false, null, null, null);
			Location location2 = new Location("A.2.1", 'A', 2, 1, false, null, null, null);
			List<Location> stock = List.of(location1,location2);
			
			when(repository.findAll()).thenReturn(stock);
			
			List<LocationResponseDTO> output = service.getAllLocations();
			
			verify(repository, times(1)).findAll();
			
			assertNotNull(output);
			assertEquals(stock.size(), output.size());
			assertEquals(stock.get(0).getLocationId(), output.get(0).locationId());
		}
	}
	
}
