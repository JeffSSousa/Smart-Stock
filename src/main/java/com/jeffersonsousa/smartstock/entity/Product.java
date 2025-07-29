package com.jeffersonsousa.smartstock.entity;

import java.util.List;

import com.jeffersonsousa.smartstock.dto.ProductRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	private String name;
	private Integer currentQuantity;
	private Integer minimumQuantity;
	private Double price;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "product")
	private List<StockMovement> stockMovements;

	public Product(ProductRequestDTO dto, Category category) {
		this.name = dto.name();
		this.currentQuantity = 0;
		this.minimumQuantity = dto.minimumQuantity();
		this.price = dto.price();
		this.category = category;
	}

}
