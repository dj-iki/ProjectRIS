package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dtos.AirportDTO;
import com.example.demo.services.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.City;
import model.Country;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/redirect")
	public ModelAndView redirectAddingCountry(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_addition");
		ModelAndView modelAndView = new ModelAndView("addingCountry");
		List<Country> countries = adminService.getCountries();
		List<City> cities = adminService.getCities();
		modelAndView.addObject("countries", countries);
		modelAndView.addObject("cities", cities);
		return modelAndView;
	}
	
	@PostMapping("/save-country")
	public String saveCountry(@RequestParam("country_name") String name, HttpServletRequest request) {
		String result = adminService.saveCountry(name);
		HttpSession session = request.getSession();
		session.removeAttribute("adding_city");
		session.removeAttribute("adding_airport");
		session.setAttribute("adding_country", result);
		return "redirect:/admin/redirect";
	}
	
	@PostMapping("/save-city")
	public String saveCity(@RequestParam("city_name") String name, @RequestParam("country") Integer countryId, HttpServletRequest request) {
		String result = adminService.saveCity(name, countryId);
		HttpSession session = request.getSession();
		session.removeAttribute("adding_country");
		session.removeAttribute("adding_airport");
		session.setAttribute("adding_country", result);
		return "redirect:/admin/redirect";
	}
	
	@ModelAttribute("airportDTO")
	public AirportDTO createAirportDTO() {
		return new AirportDTO();
	}
	
	@PostMapping("/save-airport")
	public String saveAirport(@ModelAttribute("airportDTO") AirportDTO airportDTO, HttpServletRequest request) {
		String result = adminService.saveAirport(airportDTO);
		HttpSession session = request.getSession();
		session.removeAttribute("adding_country");
		session.removeAttribute("adding_city");
		session.setAttribute("adding_airport", result);
		return "redirect:/admin/redirect";
	}
}
