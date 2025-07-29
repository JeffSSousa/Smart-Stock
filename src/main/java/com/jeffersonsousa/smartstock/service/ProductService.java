package com.jeffersonsousa.smartstock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.ProductRequestDTO;
import com.jeffersonsousa.smartstock.dto.ProductResponseDTO;
import com.jeffersonsousa.smartstock.dto.ProductUpdateDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.repository.CategoryRepository;
import com.jeffersonsousa.smartstock.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	
	public void create(ProductRequestDTO dto) {
		Optional<Category> category = categoryRepository.findById(dto.categoryId());
		Product product = new Product(dto, category.get());
		productRepository.save(product);
	}


	public ProductResponseDTO getProductById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		return new ProductResponseDTO(product.get());
	}

	
	public List<ProductResponseDTO> getAllProducts() {
		List<ProductResponseDTO> list = productRepository.findAll().stream().map(ProductResponseDTO::new).toList();
		return list;
	}


	public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
		Product update = productRepository.getReferenceById(id);
		updateDate(update, dto);
		productRepository.save(update);
		return new ProductResponseDTO(update);
	}

	private void updateDate(Product update, ProductUpdateDTO dto) {
		update.setName(dto.name());
		update.setMinimumQuantity(dto.minimumQuantity());
		update.setPrice(dto.price());
	}


	public void delete(Long id) {
		productRepository.deleteById(id);
	}

}
