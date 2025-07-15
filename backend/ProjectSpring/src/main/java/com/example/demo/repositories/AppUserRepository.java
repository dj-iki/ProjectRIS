package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer>{
	
	@Query("select au from AppUser au where au.username=:username")
	public AppUser findAppUserByUsername(@Param("username") String username);
}
