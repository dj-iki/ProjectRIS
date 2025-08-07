package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dtos.FlightInsertionDTO;
import com.example.demo.dtos.PlaneDTO;
import com.example.demo.services.FlightInsertionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Airport;
import model.AppUser;
import model.Plane;

@Controller
@RequestMapping("/manager")
public class FlightInsertionController {

	@Autowired
	FlightInsertionService flightInsertionService;
	

	@GetMapping("/redirect-addFlight")
	public ModelAndView redirectAddFlight(@CookieValue("jwt") String jwt) {
		List<Airport> airports = flightInsertionService.getAllAirports();
		List<Plane> planes = flightInsertionService.getAllPlanes(jwt);
		List<AppUser> employees = flightInsertionService.getAllEmployees(jwt);
		ModelAndView modelAndView = new ModelAndView("addingFlights");
		modelAndView.addObject("airports", airports);
		modelAndView.addObject("planes", planes);
		modelAndView.addObject("employees", employees);
		return modelAndView;
	}

	@ModelAttribute("flightInsertionDTO")
	public FlightInsertionDTO createFlightInsertionDTO() {
		return new FlightInsertionDTO();
	}

	@PostMapping("/save-flight")
	public String insertFlight(@Valid @ModelAttribute("flightInsertionDTO") FlightInsertionDTO flightInsertionDTO,
			BindingResult result, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successful_insertion");
		session.removeAttribute("unsuccessful_insertion");
		session.removeAttribute("validation_error");
		session.removeAttribute("errors");
		if (flightInsertionDTO.getArrival() != null && flightInsertionDTO.getDeparture() != null
				&& flightInsertionDTO.getArrival().before(flightInsertionDTO.departure))
			result.addError(
					new FieldError("flightInsertionDTO", "arrival", "Arrival time cannot be before departure time"));
		if (!flightInsertionService.isPlaneAvailable(flightInsertionDTO))
			result.addError(new FieldError("flightInsertionDTO", "plane", "Plane is not available at that time"));
		if (flightInsertionDTO.getFromAirport() == flightInsertionDTO.getToAirport())
			result.addError(new FieldError("flightInsertionDTO", "toAirport",
					"You cannot select 'from' and 'to' airports to be the same"));
		if (!result.hasErrors()) {
			String insertionResult = flightInsertionService.insertFlight(flightInsertionDTO);
			System.out.println(insertionResult);
			if (insertionResult.startsWith("Successfull"))
				session.setAttribute("successful_insertion", insertionResult);
			else
				session.setAttribute("unsuccessful_insertion", insertionResult);
			return "redirect:/manager/redirect";
		} else {
			session.setAttribute("validation_error", "There was an error with validating");
			session.setAttribute("errors", result.getAllErrors());
			return "redirect:/manager/redirect";
		}

	}
	@GetMapping("/redirect-addPlane")
	public String redirectAddPlane() {
		return "addingPlane";
	}

	@ModelAttribute("planeDTO")
	public PlaneDTO createPlaneDTO() {
		return new PlaneDTO();
	}

	@PostMapping("/save-plane")
	public String insertPlane(@Valid @ModelAttribute("planeDTO") PlaneDTO planeDTO, @CookieValue("jwt") String jwt,
			BindingResult result, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successful_insertion");
		session.removeAttribute("unsuccessful_insertion");
		session.removeAttribute("validation_error");
		session.removeAttribute("errors");
		if (planeDTO.getNumberOfEconomySeats() != null && planeDTO.getNumberOfEconomyRows() != null
				&& planeDTO.getNumberOfEconomySeats() % planeDTO.getNumberOfEconomyRows() != 0)
			result.addError(new FieldError("planeDTO", "numberOfEconomySeats",
					"Number of economy seats must be devisialbe with number of economy rows"));
		if (planeDTO.getNumberOfEconomyPlusSeats() != null && planeDTO.getNumberOfEconomyPlusRows() != null
				&& planeDTO.getNumberOfEconomyPlusSeats() % planeDTO.getNumberOfEconomyPlusRows() != 0)
			result.addError(new FieldError("planeDTO", "numberOfEconomySeats",
					"Number of economy plus seats must be devisialbe with number of economy plus rows"));
		if (planeDTO.getNumberOfBusinessSeats() != null && planeDTO.getNumberOfBusinessRows() != null
				&& planeDTO.getNumberOfBusinessSeats() % planeDTO.getNumberOfBusinessRows() != 0)
			result.addError(new FieldError("planeDTO", "numberOfEconomySeats",
					"Number of economy seats must be devisialbe with number of economy rows"));
		if (!result.hasErrors()) {
			String insertionResult = flightInsertionService.insertPlane(planeDTO, jwt);
			if (insertionResult.startsWith("Successfull")) {
				session.setAttribute("successful_insertion", insertionResult);
			} else {
				session.setAttribute("unsuccessful_insertion", insertionResult);
			}
		} else {
			session.setAttribute("validation_error", "There was an error with validating");
			session.setAttribute("errors", result.getAllErrors());
		}
		return "addingPlane";
	}
	
	@GetMapping("/redirect-hire")
	public String redirectHire() {
		return "hireUser";
	}
	
	@PostMapping("/hire-user")
	public String hireUser(@RequestParam("username") String username, @CookieValue("jwt") String jwt, Model model) {
		String result = flightInsertionService.hireUser(username, jwt);
		model.addAttribute("result", result);
		return "hireUser";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		simpleDateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
	}

}
