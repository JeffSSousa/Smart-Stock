package com.jeffersonsousa.smartstock.entity;

import java.util.List;

import com.jeffersonsousa.smartstock.dto.CategoryRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category(CategoryRequestDTO category) {
		this.name = category.name();
	}
	
	

}
