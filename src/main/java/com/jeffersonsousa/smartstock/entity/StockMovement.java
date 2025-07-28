package com.jeffersonsousa.smartstock.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
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
	
	private Long id;
	private String type;
	private Double quantity;
	private Instant movimentationDate;
	
	//id da posição
	//id do produto

}
