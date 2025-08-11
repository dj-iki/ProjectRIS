package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer>{
	
	@Query("select a from Airline a where a.name=:name")
	Airline findByName(@Param("name") String name);
	
	@Query("select a from Airline a where a.iataCode=:iataCode")
	Airline findByIataCode(@Param("iataCode") String iataCode);
	
	@Query("select a from Airline a where a.icaoCode=:icaoCode")
	Airline findByIcaoCode(@Param("icaoCode") String icaoCode);
}
