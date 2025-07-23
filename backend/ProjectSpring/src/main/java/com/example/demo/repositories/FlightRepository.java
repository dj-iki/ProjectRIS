package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>{

}
