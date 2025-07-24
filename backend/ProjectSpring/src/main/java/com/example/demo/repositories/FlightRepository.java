package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airport;
import model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>{
	@Query("select f from Flight f where f.airport1=:from and f.airport2=:to and f.departureTime>:departureFromTime and f.departureTime<:departureToTime")
	List<Flight> getAllFlightsFromTo(@Param("from")Airport from, @Param("to") Airport to, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime);
}
