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

import com.example.demo.dtos.FlightDelayDTO;
import com.example.demo.dtos.FlightInsertionDTO;
import com.example.demo.dtos.PlaneDTO;
import com.example.demo.services.ManagerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Airport;
import model.AppUser;
import model.Flight;
import model.Plane;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	ManagerService managerService;
	

	@GetMapping("/redirect-addFlight")
	public ModelAndView redirectAddFlight(@CookieValue("jwt") String jwt, HttpServletRequest request) {
		List<Airport> airports = managerService.getAllAirports();
		List<Plane> planes = managerService.getAllPlanes(jwt);
		List<AppUser> employees = managerService.getAllEmployees(jwt);
		ModelAndView modelAndView = new ModelAndView("addingFlights");
		modelAndView.addObject("airports", airports);
		modelAndView.addObject("planes", planes);
		modelAndView.addObject("employees", employees);
		HttpSession session = request.getSession();
		session.removeAttribute("successful_insertion");
		session.removeAttribute("unsuccessful_insertion");
		session.removeAttribute("validation_error");
		session.removeAttribute("errors");
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
		session.removeAttribute("validation_error_flight");
		session.removeAttribute("errors_flight");
		if (flightInsertionDTO.getArrival() != null && flightInsertionDTO.getDeparture() != null
				&& flightInsertionDTO.getArrival().before(flightInsertionDTO.departure))
			result.addError(
					new FieldError("flightInsertionDTO", "arrival", "Arrival time cannot be before departure time"));
		if (!managerService.isPlaneAvailable(flightInsertionDTO))
			result.addError(new FieldError("flightInsertionDTO", "plane", "Plane is not available at that time"));
		if (flightInsertionDTO.getFromAirport() == flightInsertionDTO.getToAirport())
			result.addError(new FieldError("flightInsertionDTO", "toAirport",
					"You cannot select 'from' and 'to' airports to be the same"));
		if (!result.hasErrors()) {
			String insertionResult = managerService.insertFlight(flightInsertionDTO);
			System.out.println(insertionResult);
			if (insertionResult.startsWith("Successfull"))
				session.setAttribute("successful_insertion", insertionResult);
			else
				session.setAttribute("unsuccessful_insertion", insertionResult);
			return "redirect:/manager/redirect-inside-flight";
		} else {
			session.setAttribute("validation_error", "There was an error with validating");
			session.setAttribute("errors", result.getAllErrors());
			return "redirect:/manager/redirect-inside-flight";
		}

	}
	@GetMapping("/redirect-addPlane")
	public String redirectAddPlane(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successful_insertion");
		session.removeAttribute("unsuccessful_insertion");
		session.removeAttribute("validation_error");
		session.removeAttribute("errors");
		return "addingPlane";
	}

	@ModelAttribute("planeDTO")
	public PlaneDTO createPlaneDTO() {
		return new PlaneDTO();
	}

	@PostMapping("/save-plane")
	public String insertPlane(@Valid @ModelAttribute("planeDTO") PlaneDTO planeDTO, BindingResult result, @CookieValue("jwt") String jwt,
			 HttpServletRequest request) {
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
			String insertionResult = managerService.insertPlane(planeDTO, jwt);
			if (insertionResult.startsWith("Successfull")) {
				session.setAttribute("successful_insertion", insertionResult);
			} else {
				session.setAttribute("unsuccessful_insertion", insertionResult);
			}
		} else {
			session.setAttribute("validation_error", "There was an error with validating");
			session.setAttribute("errors", result.getAllErrors());
		}
		return "redirect:/manager/redirect-inside-plane";
	}
	
	@GetMapping("/redirect-hire")
	public String redirectHire() {
		return "hireUser";
	}
	
	@PostMapping("/hire-user")
	public String hireUser(@RequestParam("username") String username, @CookieValue("jwt") String jwt, Model model) {
		String result = managerService.hireUser(username, jwt);
		model.addAttribute("result", result);
		return "hireUser";
	}
	
	@ModelAttribute("flightDelayDTO")
	public FlightDelayDTO createFlightDelayDTO() {
		return new FlightDelayDTO();
	}
	
	@GetMapping("/redirect-delay")
	public ModelAndView redirectDelay(@CookieValue("jwt") String jwt, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("delayFlight");
		List<Flight> flights = managerService.getFlights(jwt);
		modelAndView.addObject("flights", flights);
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_delay");
		session.removeAttribute("unsuccessfull_delay");
		session.removeAttribute("successfull_cancel");
		session.removeAttribute("usuccessfull_cancel");
		return modelAndView;
	}
	
	@GetMapping("/redirect-inside-delay")
	public ModelAndView redirectInsideDelay(@CookieValue("jwt") String jwt) {
		ModelAndView modelAndView = new ModelAndView("delayFlight");
		List<Flight> flights = managerService.getFlights(jwt);
		modelAndView.addObject("flights", flights);
		return modelAndView;
	}

	@PostMapping("/delay-flight")
	public String delayFlight(@ModelAttribute("flightDelayDTO") FlightDelayDTO flightDelayDTO, HttpServletRequest request, Model model) {
		String result = managerService.delayFlight(flightDelayDTO.getFlightId(), flightDelayDTO.getDeparture(), flightDelayDTO.getArrival());
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_delay");
		session.removeAttribute("unsuccessfull_delay");
		session.removeAttribute("successfull_cancel");
		session.removeAttribute("usuccessfull_cancel");
		if(result.startsWith("Success")) {
			session.setAttribute("successfull_delay", result);
			return "redirect:/manager/redirect-inside-delay";
		}else {
			session.setAttribute("unsuccessfull_delay", result);
			return "redirect:/manager/redirect-inside-delay";
		}
	}
	
	@PostMapping("/cancel-flight")
	public String cancelFlight(@RequestParam("flightId") Integer flightId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_delay");
		session.removeAttribute("unsuccessfull_delay");
		session.removeAttribute("successfull_cancel");
		session.removeAttribute("usuccessfull_cancel");
		String result = managerService.cancelFlight(flightId);
		if(result.startsWith("Success")) {
			session.setAttribute("successfull_cancel", result);
			return "redirect:/manager/redirect-inside-delay";
		}
		session.setAttribute("unsuccessfull_cancel", result);
		return "redirect:/manager/redirect-inside-delay";
	}
	
	@GetMapping("/redirect-inside-flight")
	public ModelAndView redirectInsideFlight(@CookieValue("jwt") String jwt) {
		List<Airport> airports = managerService.getAllAirports();
		List<Plane> planes = managerService.getAllPlanes(jwt);
		List<AppUser> employees = managerService.getAllEmployees(jwt);
		ModelAndView modelAndView = new ModelAndView("addingFlights");
		modelAndView.addObject("airports", airports);
		modelAndView.addObject("planes", planes);
		modelAndView.addObject("employees", employees);
		return modelAndView;
	}
	
	@GetMapping("/redirect-inside-plane")
	public String redirectInsidePlane() {
		return "addingPlane";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		simpleDateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
	}

}
