package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AppUserRegisterDTO;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.RoleRepository;

import model.AppUser;
import model.Role;

@Service
public class RegistrationService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	private Role getRoleById(Integer id) {
		return roleRepository.findById(id).get();
	}

	public String saveAppUser(AppUserRegisterDTO appUserDTO) {
		try{
			if(appUserRepository.findAppUserByUsername(appUserDTO.getUsername())==null) {
				if(appUserRepository.findAppUserByEmail(appUserDTO.getEmail()) == null) {
				AppUser appUser = new AppUser();
				appUser.setUsername(appUserDTO.getUsername());
				appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
				appUser.setEmail(appUserDTO.getEmail());
				appUser.setName(appUserDTO.getName());
				appUser.setSurname(appUserDTO.getSurname());
				appUser.setRole(getRoleById(1));
				
				appUser = appUserRepository.save(appUser);
				return "Successful registration";
				}else
					return "Error with registration - Email \"" + appUserDTO.getEmail() + "\" is already in use. Please enter another.";
			}else {
				return "Error with registration - Username \"" + appUserDTO.getUsername() + "\" is already in use. Please enter another.";
			}
		}catch(Exception e) {
			
			return "Error with registration - " + e.getMessage();
		}
		
	}

}
