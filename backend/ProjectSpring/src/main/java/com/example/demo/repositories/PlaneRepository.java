package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airline;
import model.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Integer>{
	
	@Query("select p from Plane p where p.airline=:airline")
	List<Plane> findAllByAirline(@Param("airline") Airline airline);

	@Query("select p from Plane p where p.registrationNumber=:registrationNumber")
	Plane findByRegistrationNumber(@Param("registrationNumber") String registrationNumber);
	
}
