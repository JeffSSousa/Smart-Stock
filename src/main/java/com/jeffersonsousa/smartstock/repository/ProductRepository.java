package com.jeffersonsousa.smartstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonsousa.smartstock.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
