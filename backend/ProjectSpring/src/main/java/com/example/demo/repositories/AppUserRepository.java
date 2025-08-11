package com.example.demo.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import model.Airline;
import model.AppUser;
import model.Flight;
import model.Role;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	@Query("select au from AppUser au where au.username=:username and au.deleted=false")
	public AppUser findAppUserByUsername(@Param("username") String username);

	@Transactional
	@Modifying
	@Query("update AppUser au set au.name=:name where au.username=:username")
	public int updateAppUserName(@Param("name") String name, @Param("username") String username);

	@Transactional
	@Modifying
	@Query("update AppUser au set au.surname=:surname where au.username=:username")
	public int updateAppUserSurname(@Param("surname") String surname, @Param("username") String username);

	@Transactional
	@Modifying
	@Query("update AppUser au set au.email=:email where au.username=:username")
	public int updateAppUserEmail(@Param("email") String email, @Param("username") String username);

	@Transactional
	@Modifying
	@Query("update AppUser au set au.username=:newUsername where au.username=:oldUsername")
	public int updateAppUserUsername(@Param("oldUsername") String oldUsername,
			@Param("newUsername") String newUsername);

	@Transactional
	@Modifying
	@Query("update AppUser au set au.password=:password where au.username=:username")
	public int updateAppUserPassword(@Param("password") String password, @Param("username") String username);

	@Query("select au from AppUser au where au.airline=:airline and au.role.name='EMPLOYEE' and au.deleted=false")
	public List<AppUser> findAllEmployees(@Param("airline") Airline airline);

	@Transactional
	@Modifying
	@Query("update AppUser au set au.role=:role, au.airline=:airline where au=:appUser and au.deleted=false")
	public int hireAppUser(@Param("appUser") AppUser appUser, @Param("role") Role role,
			@Param("airline") Airline airline);
	
	@Transactional
	@Modifying
	@Query("update AppUser au set au.deleted=true where au=:appUser")
	public int deleteUser(@Param("appUser") AppUser appUser);
	
	@Query("select au from AppUser au where au.email=:email and au.deleted=false")
	public AppUser findAppUserByEmail(@Param("email") String email);
	
	@Query("select au from AppUser au inner join au.bookings b where b.flight=:flight")
	public List<AppUser> getRecipients(@Param("flight") Flight flight);
	
	@Query("select distinct au.email from AppUser au inner join au.bookings b where au.deleted=false and b.flight=:flight")
	public List<String> getEmailsForNotification(@Param("flight") Flight flight);
	
	@Transactional
	@Modifying
	@Query("update AppUser au set au.role=:role where au=:appUser")
	public int promoteEmployee(@Param("role") Role role, @Param("appUser") AppUser appUser);
	
	@Transactional
	@Modifying
	@Query("update AppUser au set au.role=:role, au.airline=:airline where au=:appUser")
	public int promoteUser(@Param("role") Role role, @Param("appUser") AppUser appUser, @Param("airline") Airline airline);
}
