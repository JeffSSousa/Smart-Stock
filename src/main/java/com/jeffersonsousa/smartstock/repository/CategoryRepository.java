package com.jeffersonsousa.smartstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonsousa.smartstock.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
