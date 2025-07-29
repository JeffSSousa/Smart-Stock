package com.jeffersonsousa.smartstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonsousa.smartstock.entity.Location;

public interface LocationRepository extends JpaRepository<Location, String>{

}
