package com.example.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import model.Role;

public class AppUserRegisterDTO {

	@NotNull
	@NotBlank(message = "Username can't be blank!")
	@Size(min = 4, max = 16, message = "Username must be between 4 and 16 characters long")
	private String username;

	@NotNull
	@NotBlank(message = "Password can't be blank!")
	@Pattern(regexp = "^[a-zA-Z0-9@#$%^&+=!_-]+$", message = "Password must contain at least 1 lowercase letter, 1 Upercase letter, 1 digit and 1 special character [@#$%^&+=!_-]")
	private String password;

	@NotNull
	@NotBlank(message = "Email can't be blank!")
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email is not in email format")
	private String email;

	@NotNull
	@NotBlank(message = "Name can't be blank!")
	@Size(min = 2, max = 100, message="Name must be between 2 and 100 characters long!")
	@Pattern(regexp = "^[A-Za-z ]+$", message="Name can only contain letters and spaces!")
	private String name;

	@NotNull
	@NotBlank(message = "Surname can't be blank!")
	@Size(min = 2, max = 100, message="Surname must be between 2 and 100 characters long!")
	@Pattern(regexp = "^[A-Za-z ]+$", message="Surname can only contain letters and spaces")
	private String surname;

	@NotNull
	private Integer role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	
}
