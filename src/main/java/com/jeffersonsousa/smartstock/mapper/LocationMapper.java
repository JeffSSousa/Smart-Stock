package com.jeffersonsousa.smartstock.mapper;

import com.jeffersonsousa.smartstock.dto.location.LocationResponseDTO;
import com.jeffersonsousa.smartstock.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "product.productId", target = "productId")
    LocationResponseDTO toDTO (Location location);

}
