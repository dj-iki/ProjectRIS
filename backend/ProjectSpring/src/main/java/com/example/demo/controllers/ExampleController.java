package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AuthenticationRequest;
import com.example.demo.dtos.AuthenticationResponse;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.AppUserDetailsService;
import com.example.demo.security.JWTService;

@RestController
public class ExampleController {
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	AppUserDetailsService auds;
	
	@Autowired
	AuthenticationManager manager;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
		try {
			System.out.println(request.getUsername() + " " + request.getPassword());
			AuthenticationResponse response = new AuthenticationResponse();
			manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			
			AppUserDetails aud = (AppUserDetails) auds.loadUserByUsername(request.getUsername());
			
			String token = jwtService.generateToken(aud);
			
			response.setJwt(token);
			
			return ResponseEntity.ok("Uspesno autentifikovan " + response);
		}catch(AuthenticationException e) {
			System.out.println("Greška u autentifikaciji: " + e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}catch(Exception e) {
			System.out.println("Nepoznata greška: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nepoznata greška: " + e.getMessage());
		}
	}
	
}
