package com.jeffersonsousa.smartstock.entity;

import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name = "tb_location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

	@Id
	private String locationId;
	private char aisle;
	private Integer position;
	private Integer floor;
	private Boolean hasProduct;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	private Integer quantity;

	@OneToMany(mappedBy = "location")
	private List<StockMovement> stockMovements ;
	
}
