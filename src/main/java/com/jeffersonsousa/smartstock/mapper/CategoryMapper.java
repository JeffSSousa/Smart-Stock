package com.jeffersonsousa.smartstock.mapper;

import com.jeffersonsousa.smartstock.dto.category.CategoryRequestDTO;
import com.jeffersonsousa.smartstock.dto.category.CategoryResponseDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity (CategoryRequestDTO dto);

    @Mapping(source = "category.categoryId", target = "id")
    CategoryResponseDTO toDto (Category category);
}
