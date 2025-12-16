package com.jeffersonsousa.smartstock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jeffersonsousa.smartstock.dto.product.ProductRequestDTO;
import com.jeffersonsousa.smartstock.dto.product.ProductResponseDTO;
import com.jeffersonsousa.smartstock.dto.product.ProductUpdateDTO;
import com.jeffersonsousa.smartstock.entity.Category;
import com.jeffersonsousa.smartstock.entity.Product;
import com.jeffersonsousa.smartstock.exception.exceptions.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.exceptions.DatabaseException;
import com.jeffersonsousa.smartstock.repository.CategoryRepository;
import com.jeffersonsousa.smartstock.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	
	public void create(Product product, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
							.orElseThrow(() -> new ControllerNotFoundException("Não foi encontrada uma categoria com o ID: " + categoryId));

        product.setCategory(category);
        product.setCurrentQuantity(0);
		productRepository.save(product);
	}


	public Product getProductById(Long id) {
		return productRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Produto com o ID" + id + " não foi encontrado!!"));
	}

	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> getLowStockProducts() {
		return productRepository.findAll().stream().filter(p -> p.getCurrentQuantity() < p.getMinimumQuantity()).toList();
	}


	public Product update(Long id, Product product) {
		Product update = productRepository.findById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Produto com o ID" + id + " não foi encontrado!!"));
		
		updateDate(update, product);
		productRepository.save(update);
		return update;
	}

	private void updateDate(Product update, Product product) {
        if (product.getName() != null) update.setName(product.getName());
        if (product.getMinimumQuantity() != null) update.setMinimumQuantity(product.getMinimumQuantity());
        if (product.getPrice() != null) update.setPrice(product.getPrice());
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
