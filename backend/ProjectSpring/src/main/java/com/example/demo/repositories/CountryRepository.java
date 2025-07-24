package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airport;
import model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	@Query("select c from Country c " + "inner join c.cities city " + "inner join city.airports a "
			+ "inner join a.flights1 f "
			+ "where f.airport1=:fromAirport and f.departureTime>:departureFromTime and f.departureTime<:departureToTime")
	List<Country> getToCountries(@Param("fromAirport") Airport fromAirport,
			@Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime);

}
