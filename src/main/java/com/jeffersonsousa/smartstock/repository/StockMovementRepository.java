package com.jeffersonsousa.smartstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonsousa.smartstock.entity.StockMovement;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long>{

}
