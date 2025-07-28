package com.jeffersonsousa.smartstock.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private String aisle;
	private String position;
	private String floor;
	private Boolean hasProduct;

	@OneToMany(mappedBy = "location")
	private List<StockMovement> stockMovements ;
	
}
