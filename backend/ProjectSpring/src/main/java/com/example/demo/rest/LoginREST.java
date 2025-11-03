package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AppUserRegisterDTO;
import com.example.demo.dtos.AuthenticationRequest;
import com.example.demo.dtos.AuthenticationResponse;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.AppUserDetailsService;
import com.example.demo.security.JWTService;
import com.example.demo.services.RegistrationService;

@RestController
@RequestMapping("/api/auth")
public class LoginREST {

    private final AppUserRepository appUserRepository;
	
	@Autowired
	AuthenticationManager authMgr;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	AppUserDetailsService userDetailsService;
	
	@Autowired
	RegistrationService registrationService;

    LoginREST(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
		try {
			AuthenticationResponse res = new AuthenticationResponse();
			authMgr.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			
			AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(request.getUsername());
			
			String jwt = jwtService.generateToken(userDetails);
			
			res.setJwt(jwt);
			
			return ResponseEntity.ok(res);
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody AppUserRegisterDTO appUserRegisterDTO){
		try {
			AuthenticationResponse res = new AuthenticationResponse();
			String result = registrationService.saveAppUser(appUserRegisterDTO);
			
			if(result.startsWith("Error")) 
				return ResponseEntity.badRequest().build();
			
			authMgr.authenticate(new UsernamePasswordAuthenticationToken(appUserRegisterDTO.getUsername(), appUserRegisterDTO.getPassword()));
			
			AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(appUserRegisterDTO.getUsername());
			
			String jwt = jwtService.generateToken(userDetails);
			
			res.setJwt(jwt);
			
			return ResponseEntity.ok(res);
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

}
