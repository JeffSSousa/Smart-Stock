package com.jeffersonsousa.smartstock.mapper;

import com.jeffersonsousa.smartstock.dto.product.ProductRequestDTO;
import com.jeffersonsousa.smartstock.dto.product.ProductResponseDTO;
import com.jeffersonsousa.smartstock.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity (ProductRequestDTO dto);

    ProductResponseDTO toDTO (Product product);

}
