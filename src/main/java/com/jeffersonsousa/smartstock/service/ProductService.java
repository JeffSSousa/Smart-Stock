package com.jeffersonsousa.smartstock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.ProductRequestDTO;
import com.jeffersonsousa.smartstock.dto.ProductResponseDTO;
import com.jeffersonsousa.smartstock.dto.ProductUpdateDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.exception.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.DatabaseException;
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
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Produto com o ID" + id + " não foi encontrado!!"));
		
		return new ProductResponseDTO(product);
	}

	
	public List<ProductResponseDTO> getAllProducts() {
		List<ProductResponseDTO> list = productRepository.findAll().stream().map(ProductResponseDTO::new).toList();
		return list;
	}
	
	public List<ProductResponseDTO> getLowStockProducts() {
		List<ProductResponseDTO> list = productRepository.findAll().stream().filter(p -> p.getCurrentQuantity() < p.getMinimumQuantity()).map(ProductResponseDTO::new).toList();
		return list;
	}


	public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
		Product update = productRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Produto com o ID" + id + " não foi encontrado!!"));
		
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
		
		try {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Produto com o ID" + id + " não foi encontrado!!"));
		
		productRepository.delete(product);
		
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Não é possível excluir o produto. Ele está vinculado a outras entidades no sistema.");
		}
	}

}
