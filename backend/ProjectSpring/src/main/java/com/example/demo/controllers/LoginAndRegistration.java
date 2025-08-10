package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.AppUserLoginDTO;
import com.example.demo.dtos.AppUserRegisterDTO;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.AppUserDetailsService;
import com.example.demo.security.JWTService;
import com.example.demo.services.RegistrationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Role;

@Controller
@RequestMapping("/auth")
public class LoginAndRegistration {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private AppUserDetailsService userService;
	
	@Autowired
	private RegistrationService registrationService;
	
	@GetMapping("/redirect-login")
	public String showLoginForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("appUserLoginDTO") AppUserLoginDTO appUserLoginDTO, BindingResult result, HttpServletResponse response, Model model) {
		if(!result.hasErrors()) {
			try {
				authManager.authenticate(new UsernamePasswordAuthenticationToken(appUserLoginDTO.getUsername(), appUserLoginDTO.getPassword()));
				AppUserDetails userDetails = (AppUserDetails) userService.loadUserByUsername(appUserLoginDTO.getUsername());
				String token = jwtService.generateToken(userDetails);
				
	
		        Cookie jwtCookie = new Cookie("jwt", token);
		        jwtCookie.setHttpOnly(true);
		        jwtCookie.setPath("/");
		        jwtCookie.setMaxAge(60*60);
		        response.addCookie(jwtCookie);
				
				return "redirect:/search/redirect";
			}catch(Exception e) {
				model.addAttribute("exception", e.getMessage());
				return "login";
			}
		}else {
			model.addAttribute("validation_error", "There was an error with validating");
			model.addAttribute("errors", result.getAllErrors());
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
	    Cookie cookie = new Cookie("jwt", null);
	    cookie.setPath("/");                    
	    cookie.setMaxAge(0);                    
	    cookie.setHttpOnly(true);               
	    cookie.setSecure(false);

	    response.addCookie(cookie);

	    return "login";
	}


	@ModelAttribute("appUserLoginDTO")
	public AppUserLoginDTO createAppUserLoginDTO() {
		return new AppUserLoginDTO();
	}
	
	@ModelAttribute("appUserRegisterDTO")
	public AppUserRegisterDTO createAppUserRegisterDTO() {
		return new AppUserRegisterDTO();
	}
	
	@ModelAttribute("roles")
	public List<Role> getAllRoles(){
		return registrationService.getAllRoles();
	}
	
	@GetMapping("/redirect-register")
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("appUserRegisterDTO") AppUserRegisterDTO appUserRegisterDTO, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			String registrationResult = registrationService.saveAppUser(appUserRegisterDTO);
			if(registrationResult.startsWith("Error")) {
				model.addAttribute("error_with_adding_user", registrationResult);
				return "registration";
			}
			else {
				model.addAttribute("successful_registration", "Registration was seccessful. Pleas log in");
				return "login";
			}
		};
		model.addAttribute("validation_error", "There was an error with validating");
		model.addAttribute("errors", result.getAllErrors());
		return "registration";
	}
}
