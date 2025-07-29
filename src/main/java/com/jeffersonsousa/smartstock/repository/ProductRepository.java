package com.jeffersonsousa.smartstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonsousa.smartstock.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
