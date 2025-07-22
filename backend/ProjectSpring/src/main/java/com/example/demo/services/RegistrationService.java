package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	private Role getRoleById(Integer id) {
		return roleRepository.findById(id).get();
	}

	public boolean saveAppUser(AppUserRegisterDTO appUserDTO) {
		try{
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			AppUser appUser = new AppUser();
			appUser.setUsername(appUserDTO.getUsername());
			appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
			appUser.setEmail(appUserDTO.getEmail());
			appUser.setName(appUserDTO.getName());
			appUser.setSurname(appUserDTO.getSurname());
			appUser.setRole(getRoleById(appUserDTO.getRole()));
			
			appUser = appUserRepository.save(appUser);
			return true;
		}catch(Exception e) {
			System.out.println("There was an error with registration");
			return false;
		}
		
	}

}
