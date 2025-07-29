package com.jeffersonsousa.smartstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonsousa.smartstock.entity.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long>{

}
