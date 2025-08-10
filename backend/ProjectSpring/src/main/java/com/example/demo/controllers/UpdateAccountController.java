package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.AppUserDTO;
import com.example.demo.services.UpdateAccountService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class UpdateAccountController {

	@Autowired
	UpdateAccountService updateAccountService;

	@GetMapping("/redirect")
	public String redirectAccount(@CookieValue("jwt") String jwt, Model model, HttpServletRequest request) {
		AppUserDTO appUserDTO = updateAccountService.getAppUser(jwt);
		model.addAttribute("appUserDTO", appUserDTO);
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		session.removeAttribute("unsuccessfull_update");
		return "updateAccount";
	}

	@ModelAttribute("appUserDTO")
	public AppUserDTO createAppUserDTO() {
		return new AppUserDTO();
	}

	@PostMapping("/updateName")
	public String updateName(@ModelAttribute("appUserDTO") AppUserDTO appUserDTO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		session.removeAttribute("unsuccessfull_update");
		if (updateAccountService.updateName(appUserDTO.getOldUsername(), appUserDTO.getName()))
			session.setAttribute("successfull_update", "Name updated successfully");
		else
			session.setAttribute("unsuccessfull_update", "Error while updating name");
		return "redirect:/account/redirect";
	}

	@PostMapping("/updateSurname")
	public String updateSurname(@ModelAttribute("appUserDTO") AppUserDTO appUserDTO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		session.removeAttribute("unsuccessfull_update");
		if (updateAccountService.updateSurname(appUserDTO.getOldUsername(), appUserDTO.getSurname()))
			session.setAttribute("successfull_update", "Surname updated successfully");
		else
			session.setAttribute("unsuccessfull_update", "Error while updating surname");
		return "redirect:/account/redirect";
	}

	@PostMapping("/updateEmail")
	public String updateEmail(@ModelAttribute("appUserDTO") AppUserDTO appUserDTO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		session.removeAttribute("unsuccessfull_update");
		if (updateAccountService.updateEmail(appUserDTO.getOldUsername(), appUserDTO.getEmail()))
			session.setAttribute("successfull_update", "Email updated successfully");
		else
			session.setAttribute("unsuccessfull_update", "Error while updating email");
		return "redirect:/account/redirect";
	}

	@PostMapping("/updateUsername")
	public String updateUsername(@ModelAttribute("appUserDTO") AppUserDTO appUserDTO, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		session.removeAttribute("unsuccessfull_update");
		String result = updateAccountService.updateUsername(appUserDTO.getOldUsername(), appUserDTO.getNewUsername());
		if (!result.startsWith("Error")) {
			Cookie jwtCookie = new Cookie("jwt", result);
			jwtCookie.setHttpOnly(true);
			jwtCookie.setPath("/");
			jwtCookie.setMaxAge(60 * 60);
			response.addCookie(jwtCookie);
			session.setAttribute("successfull_update", "Successfully updated username");
		} else
			session.setAttribute("unsuccessfull_update", result);
		return "redirect:/account/redirect";
	}

	@PostMapping("/updatePassword")
	public String updatePassword(@ModelAttribute("appUserDTO") AppUserDTO appUserDTO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		session.removeAttribute("unsuccessfull_update");
		String result = updateAccountService.updatePassword(appUserDTO.getOldPassword(), appUserDTO.getNewPassword(),
				appUserDTO.getOldUsername());
		if (!result.startsWith("Error")) {
			session.setAttribute("successfull_update", result);
		} else
			session.setAttribute("unsuccessfull_update", result);
		return "redirect:/account/redirect";
	}
	
	@PostMapping("/delete")
	public String deleteAccount(@ModelAttribute("appUserDTO") AppUserDTO appUserDTO, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_update");
		session.removeAttribute("unsuccessfull_update");
		String result = updateAccountService.deleteAccount(appUserDTO.getOldUsername(), appUserDTO.getOldPassword());
		if (!result.startsWith("Error")) {
			Cookie cookie = new Cookie("jwt", null);
			cookie.setPath("/");                    
		    cookie.setMaxAge(0);                    
		    cookie.setHttpOnly(true);
		    cookie.setSecure(false);
		    response.addCookie(cookie);
			session.setAttribute("successfull_update", result);
			return "redirect:/search/redirect";
		}
		session.setAttribute("unsuccessfull_update", result);
		return "redirect:/account/redirect";
	}
}
