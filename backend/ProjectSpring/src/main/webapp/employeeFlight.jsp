<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Flights</title>
<link rel="stylesheet" type="text/css" href="style/flights.css">
</head>
<body>
    <h1>Available Flights</h1>

    <c:if test="${!empty flights}">
    	<div class="flight-container">
	        <c:forEach items="${flights}" var="flight">
	            <div class="flight-card">
	                <div class="flight-header">
	                    ${flight.flightNumber} — ${flight.plane.airline.name}
	                </div>
	                <div class="flight-info">
	                    ${flight.airport1.name} (${flight.airport1.iataCode})
	                    <span class="arrow">→</span>
	                    ${flight.airport2.name} (${flight.airport2.iataCode})
	                </div>
	                <div class="flight-info">
	                    ${flight.departureTime} <span class="arrow">→</span> ${flight.arrivalTime}
	                </div>
	            </div>
	        </c:forEach>
        </div>
    </c:if>

    <c:if test="${!empty message}">
        <p class="message">${message}</p>
    </c:if>
</body>
</html>
