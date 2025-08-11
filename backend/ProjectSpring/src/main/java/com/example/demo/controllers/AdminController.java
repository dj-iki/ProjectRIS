package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dtos.AirlineDTO;
import com.example.demo.dtos.AirportDTO;
import com.example.demo.services.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Airline;
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
		session.removeAttribute("adding_city");
		session.removeAttribute("adding_airport");
		session.removeAttribute("adding_country");
		session.removeAttribute("adding_airline");
		session.removeAttribute("promote_employee");
		session.removeAttribute("validation_error_airline");
		session.removeAttribute("errors_airline");
		session.removeAttribute("validation_error_airport");
		session.removeAttribute("errors_airport");
		ModelAndView modelAndView = new ModelAndView("admin");
		List<Country> countries = adminService.getCountries();
		List<City> cities = adminService.getCities();
		List<Airline> airlines = adminService.getAirlines();
		modelAndView.addObject("countries", countries);
		modelAndView.addObject("cities", cities);
		modelAndView.addObject("airlines", airlines);
		return modelAndView;
	}
	
	@PostMapping("/save-country")
	public String saveCountry(@RequestParam("country_name") String name, HttpServletRequest request) {
		String result = adminService.saveCountry(name);
		HttpSession session = request.getSession();
		session.removeAttribute("adding_city");
		session.removeAttribute("adding_airport");
		session.removeAttribute("promote_employee");
		session.removeAttribute("adding_airline");
		session.removeAttribute("validation_error_airline");
		session.removeAttribute("errors_airline");
		session.removeAttribute("validation_error_airport");
		session.removeAttribute("errors_airport");
		session.setAttribute("adding_country", result);
		return "redirect:/admin/redirect-inside";
	}
	
	@PostMapping("/save-city")
	public String saveCity(@RequestParam("city_name") String name, @RequestParam("country") Integer countryId, HttpServletRequest request) {
		String result = adminService.saveCity(name, countryId);
		HttpSession session = request.getSession();
		session.removeAttribute("adding_country");
		session.removeAttribute("adding_airport");
		session.removeAttribute("promote_employee");
		session.removeAttribute("adding_airline");
		session.removeAttribute("validation_error_airline");
		session.removeAttribute("errors_airline");
		session.removeAttribute("validation_error_airport");
		session.removeAttribute("errors_airport");
		session.setAttribute("adding_country", result);
		return "redirect:/admin/redirect-inside";
	}
	
	@ModelAttribute("airportDTO")
	public AirportDTO createAirportDTO() {
		return new AirportDTO();
	}
	
	@PostMapping("/save-airport")
	public String saveAirport(@Valid @ModelAttribute("airportDTO") AirportDTO airportDTO, BindingResult bindingResult, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute("adding_country");
		session.removeAttribute("adding_city");
		session.removeAttribute("promote_employee");
		session.removeAttribute("adding_airline");
		session.removeAttribute("validation_error_airline");
		session.removeAttribute("errors_airline");
		if(!bindingResult.hasErrors()) {
			String result = adminService.saveAirport(airportDTO);
			session.setAttribute("adding_airport", result);
		}else {
			session.setAttribute("validation_error_airport", "There was an error with validating airport data");
			session.setAttribute("errors_airport", bindingResult.getAllErrors());
		}
		return "redirect:/admin/redirect-inside";

	}
	
	@ModelAttribute("airlineDTO")
	public AirlineDTO createAirlineDTO() {
		return new AirlineDTO();
	}
	
	@PostMapping("/save-airline")
	public String saveAirport(@Valid @ModelAttribute("airlineDTO") AirlineDTO airlineDTO, BindingResult bindingResult, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("adding_country");
		session.removeAttribute("adding_city");
		session.removeAttribute("adding_airport");
		session.removeAttribute("promote_employee");
		session.removeAttribute("validation_error_airport");
		session.removeAttribute("errors_airport");
		if(!bindingResult.hasErrors()) {
			String result = adminService.saveAirline(airlineDTO);
			session.setAttribute("adding_airline", result);
		}else {
			session.setAttribute("validation_error_airline", "There was an error with validating airport data");
			session.setAttribute("errors_airline", bindingResult.getAllErrors());
		}
		return "redirect:/admin/redirect-inside";
	}
	
	@PostMapping("/promote-employee")
	public String promoteEmplyee(@RequestParam("username") String username, @RequestParam("airline") Integer airligneId, HttpServletRequest request) {
		String result = adminService.promoteEmployee(username, airligneId);
		HttpSession session = request.getSession();
		session.removeAttribute("adding_country");
		session.removeAttribute("adding_city");
		session.removeAttribute("adding_airport");
		session.removeAttribute("adding_airline");
		session.removeAttribute("validation_error_airline");
		session.removeAttribute("errors_airline");
		session.removeAttribute("validation_error_airport");
		session.removeAttribute("errors_airport");
		session.setAttribute("promote_employee", result);
		return "redirect:/admin/redirect-inside";
	}
	
	@GetMapping("/redirect-inside")
	public ModelAndView redirecInside() {
		ModelAndView modelAndView = new ModelAndView("admin");
		List<Country> countries = adminService.getCountries();
		List<City> cities = adminService.getCities();
		List<Airline> airlines = adminService.getAirlines();
		modelAndView.addObject("countries", countries);
		modelAndView.addObject("cities", cities);
		modelAndView.addObject("airlines", airlines);
		return modelAndView;
	}
}
