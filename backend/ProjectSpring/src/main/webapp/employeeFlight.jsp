<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Flights</title>
<link rel="stylesheet" type="text/css" href="style/flights.css">
</head>
<body>
	<div class="nav-buttons">
		<a href="/ProjectRIS/"><button>Home</button></a>
		<sec:authorize access="!isAuthenticated()">
			<a href="/ProjectRIS/auth/redirect-login"><button type="button">Log
					in</button></a>
			<a href="/ProjectRIS/auth/redirect-register"><button
					type="button">Register</button></a>
		</sec:authorize>

		<sec:authorize
			access="hasAnyRole('ROLE_USER','ROLE_EMPLOYEE','ROLE_MANAGER','ROLE_ADMIN')">
			<a href="/ProjectRIS/account/redirect"><button>Account</button></a>
			<a href="/ProjectRIS/auth/logout"><button>Log out</button></a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_MANAGER')">
			<a href="/ProjectRIS/manager/redirect-addFlight"><button>Add
					Flight</button></a>
			<a href="/ProjectRIS/manager/redirect-addPlane"><button>Add
					Plane</button></a>
			<a href="/ProjectRIS/manager/redirect-hire"><button>Hire
					User</button></a>
			<a href="/ProjectRIS/manager/redirect-delay"><button>Delay/Cancel
					Flight</button></a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<a href="/ProjectRIS/admin/redirect"><button>Admin
					Functionalities</button></a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_EMPLOYEE')">
			<a href="/ProjectRIS/employee/flights"><button>See your
					flights</button></a>
		</sec:authorize>
	</div>
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
