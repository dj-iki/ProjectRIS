package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Integer>{

}
