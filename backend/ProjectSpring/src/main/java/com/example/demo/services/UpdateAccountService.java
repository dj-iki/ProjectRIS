package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AppUserDTO;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.AppUserDetailsService;
import com.example.demo.security.JWTService;

import model.AppUser;

@Service
public class UpdateAccountService {
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	AppUserDetailsService userService;
	
	public AppUserDTO getAppUser(String jwt) {
		String username = jwtService.extractUsername(jwt);
		AppUser appUser =  appUserRepository.findAppUserByUsername(username);
		AppUserDTO appUserDTO = new AppUserDTO();
		appUserDTO.setName(appUser.getName());
		appUserDTO.setSurname(appUser.getSurname());
		appUserDTO.setEmail(appUser.getEmail());
		appUserDTO.setOldUsername(appUser.getUsername());
		return appUserDTO;
	}

	public boolean updateName(String username, String newName) {
		if(appUserRepository.updateAppUserName(newName, username) == 1)
			return true;
		else
			return false;
	}

	public boolean updateSurname(String username, String surname) {
		if(appUserRepository.updateAppUserSurname(surname, username) == 1)
			return true;
		else
			return false;
	}
	public boolean updateEmail(String username, String email) {
		if(appUserRepository.updateAppUserEmail(email, username) == 1)
			return true;
		else
			return false;
	}
	public String updateUsername(String oldUsername, String newUsername) {
		if(appUserRepository.findAppUserByUsername(newUsername) == null) {
			if(appUserRepository.updateAppUserUsername(oldUsername, newUsername) == 1) {
				try {
					AppUserDetails userDetails = (AppUserDetails) userService.loadUserByUsername(newUsername);
					String token = jwtService.generateToken(userDetails);
					return token;
				}catch(AuthenticationException ae) {
					return "Error authenticating - " + ae.getMessage();
				}
			}else
				return "Error with updating";
		}else
			return "Error - new username is allready in use";
	}
	
	public String updatePassword(String oldPassword, String newPassword, String username) {
		AppUser appUser = appUserRepository.findAppUserByUsername(username);
		if(encoder.matches(oldPassword, appUser.getPassword())) {
			if(appUserRepository.updateAppUserPassword(encoder.encode(newPassword), username) == 1)
				return "Successfull update";
			else 
				return "Error with updating";
		}else
			return "Error - wrong password";
	}
}
