package com.jeffersonsousa.smartstock.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_stock_movement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stockMovementId;
	private String type;
	private Integer quantity;
	private Instant movimentationDate;

	//id do produto
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	//id da posição
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	

}
