package com.example.demo.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AppUserRequest;
import com.example.demo.dtos.AppUserResponse;
import com.example.demo.services.UpdateAccountService;

import jakarta.servlet.http.HttpServletRequest;
import model.AppUser;

@RestController
@RequestMapping("/api/account")
public class UpdateAccountREST {
	
	@Autowired
	UpdateAccountService accountService;
	
	@GetMapping("/account")
	public ResponseEntity<AppUserResponse> getAccount(HttpServletRequest request){
		AppUser appUser = accountService.appUser(request.getHeader("Authorization").substring(7));
		if(appUser!=null) {
			AppUserResponse appUserResponse = new AppUserResponse();
			appUserResponse.setEmail(appUser.getEmail());
			appUserResponse.setName(appUser.getName());
			appUserResponse.setSurname(appUser.getSurname());
			appUserResponse.setUsername(appUser.getUsername());
			return ResponseEntity.ok(appUserResponse);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/update-name")
	public ResponseEntity<Map<String, String>> updateName(@RequestBody AppUserRequest appUserRequest, HttpServletRequest request){
		AppUser appUser = accountService.appUser(request.getHeader("Authorization").substring(7));
		if(accountService.updateName(appUser.getUsername(), appUserRequest.getName()))
			return ResponseEntity.ok(Map.of("message", "Name changed successfully!"));
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/update-surname")
	public ResponseEntity<Map<String, String>> updateSurame(@RequestBody AppUserRequest appUserRequest, HttpServletRequest request){
		AppUser appUser = accountService.appUser(request.getHeader("Authorization").substring(7));
		if(accountService.updateSurname(appUser.getUsername(), appUserRequest.getSurname()))
			return ResponseEntity.ok(Map.of("message", "Surname changed successfully!"));
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/update-email")
	public ResponseEntity<Map<String, String>> updateEmail(@RequestBody AppUserRequest appUserRequest, HttpServletRequest request){
		AppUser appUser = accountService.appUser(request.getHeader("Authorization").substring(7));
		if(accountService.updateEmail(appUser.getUsername(), appUserRequest.getEmail()))
			return ResponseEntity.ok(Map.of("message", "Email address changed successfully!"));
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/update-username")
	public ResponseEntity<Map<String, String>> updateUsername(@RequestBody AppUserRequest appUserRequest, HttpServletRequest request){
		AppUser appUser = accountService.appUser(request.getHeader("Authorization").substring(7));
		String result = accountService.updateUsername(appUser.getUsername(), appUserRequest.getNewUsername());
		if(!result.startsWith("Error"))
			return ResponseEntity.ok(Map.of("message", "Username changed successfully!"));
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/update-password")
	public ResponseEntity<Map<String, String>> updatePassword(@RequestBody AppUserRequest appUserRequest, HttpServletRequest request){
		AppUser appUser = accountService.appUser(request.getHeader("Authorization").substring(7));
		String result = accountService.updatePassword(appUserRequest.getOldPassword(), appUserRequest.getNewPassword(), appUser.getUsername());
		if(!result.startsWith("Error"))
			return ResponseEntity.ok(Map.of("message", "Password changed successfully!"));
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Map<String, String>> delete(@RequestBody AppUserRequest appUserDTO, HttpServletRequest request){
		AppUser appUser = accountService.appUser(request.getHeader("Authorization").substring(7));
		String result = accountService.deleteAccount(appUser.getUsername(), appUserDTO.getOldPassword());
		if(!result.startsWith("Error"))
			return ResponseEntity.ok(Map.of("message", "Account deleted successfully!"));
		return ResponseEntity.badRequest().build();
	}
	
}
