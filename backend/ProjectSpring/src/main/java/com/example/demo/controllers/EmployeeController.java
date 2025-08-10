package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.services.EmployeeService;

import model.Flight;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/flights")
	public ModelAndView getFlights(@CookieValue("jwt") String jwt) {
		ModelAndView modelAndView = new ModelAndView("employeeFlight");
		List<Flight> flights = employeeService.getEmployeeFlights(jwt);
		if(flights!=null && flights.size()>0)
			modelAndView.addObject("flights", flights);
		else
			modelAndView.addObject("message", "There are no flights in the future");
		return modelAndView;
	}
	
}
