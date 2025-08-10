package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AppUserLoginDTO {
	
	@NotNull
	@NotBlank(message = "Username can't be blank!")
	@Size(min = 4, max = 16, message = "Username must be between 4 and 16 characters long")
	private String username;
	
	@NotNull
	@NotBlank(message = "Password can't be blank!")
	@Pattern(regexp = "^[a-zA-Z0-9@#$%^&+=!_-]+$", message = "Password must contain at least 1 lowercase letter, 1 Upercase letter, 1 digit and 1 special character [@#$%^&+=!_-]")
	private String password;

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
}
