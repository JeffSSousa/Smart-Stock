package com.jeffersonsousa.smartstock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeffersonsousa.smartstock.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String>{

	Optional<Location> findFirstByHasProductFalse();

}
