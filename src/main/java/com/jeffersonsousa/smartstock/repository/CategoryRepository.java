package com.jeffersonsousa.smartstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonsousa.smartstock.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
