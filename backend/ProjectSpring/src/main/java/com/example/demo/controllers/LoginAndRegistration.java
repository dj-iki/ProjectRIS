package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.AppUserLoginDTO;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.AppUserDetailsService;
import com.example.demo.security.JWTService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class LoginAndRegistration {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private AppUserDetailsService userService;
	
	@GetMapping("/redirect")
	public String showLoginForm() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("appUserLoginDTO") AppUserLoginDTO appUserLoginDTO, HttpServletResponse response, Model model) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(appUserLoginDTO.getUsername(), appUserLoginDTO.getPassword()));
			AppUserDetails userDetails = (AppUserDetails) userService.loadUserByUsername(appUserLoginDTO.getUsername());
			String token = jwtService.generateToken(userDetails);
			

	        Cookie jwtCookie = new Cookie("jwt", token);
	        jwtCookie.setHttpOnly(true);
	        jwtCookie.setPath("/");
	        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
	        response.addCookie(jwtCookie);
			
			return "index";
		}catch(AuthenticationException ae) {
			model.addAttribute("error", ae.getMessage());
			return "/auth/login";
		}
		
	}
	
	@PostMapping("/logout")
	public String logout() {
		return "/auth/login";
	}

	@ModelAttribute("appUserLoginDTO")
	public AppUserLoginDTO createAppUserLoginDTO() {
		return new AppUserLoginDTO();
	}

}
