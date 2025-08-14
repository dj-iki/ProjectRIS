<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Flight Search</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/flights.css">
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
	<c:choose>
		<c:when test="${!empty returning }">
			<form:form action="/ProjectRIS/booking/new" method="get"
				modelAttribute="bookingDTO">
				<div class="flight-container">
					<div class="flight-column">
						<c:forEach items="${flightsTo}" var="flightTo">
							<label class="flight-card"> <form:radiobutton
									path="flightIdFrom" value="${flightTo.idFlight}"
									cssClass="flight-radio" />
								<div class="flight-info">
									<p class="flight-number">${flightTo.flightNumber}-
										${flightTo.plane.airline.name}</p>
									<p class="flight-route">
										${flightTo.airport1.name} (${flightTo.airport1.iataCode}) <span
											class="arrow">→</span> ${flightTo.airport2.name}
										(${flightTo.airport2.iataCode})
									</p>
									<p class="flight-time">
										${flightTo.departureTime} <span class="arrow">→</span>
										${flightTo.arrivalTime}
									</p>
								</div>
							</label>
						</c:forEach>

					</div>
					<div class="flight-column">
						<c:forEach items="${returning}" var="flightFrom">
							<label class="flight-card"> <form:radiobutton
									path="flightIdReturning" value="${flightFrom.idFlight}"
									cssClass="flight-radio" />
								<div class="flight-info">
									<p class="flight-number">${flightFrom.flightNumber}-
										${flightFrom.plane.airline.name}</p>
									<p class="flight-route">
										${flightFrom.airport1.name} (${flightFrom.airport1.iataCode})
										<span class="arrow">→</span> ${flightFrom.airport2.name}
										(${flightFrom.airport2.iataCode})
									</p>
									<p class="flight-time">
										${flightFrom.departureTime} <span class="arrow">→</span>
										${flightFrom.arrivalTime}
									</p>
								</div>
							</label>
						</c:forEach>
					</div>
				</div>
				<form:input type="hidden" value="${numberOfSeats }"
					path="numberOfSeats" />
				<input type="submit" value="Book">
			</form:form>
		</c:when>
		<c:otherwise>

			<form:form action="/ProjectRIS/booking/new" method="get"
				modelAttribute="bookingDTO">
				<div class="flight-column">
					<c:forEach items="${flightsTo}" var="flightTo">
						<label class="flight-card"> <form:radiobutton
								path="flightIdFrom" value="${flightTo.idFlight}"
								cssClass="flight-radio" />
							<div class="flight-info">
								<p class="flight-number">${flightTo.flightNumber}-
									${flightTo.plane.airline.name}</p>
								<p class="flight-route">
									${flightTo.airport1.name} (${flightTo.airport1.iataCode}) <span
										class="arrow">→</span> ${flightTo.airport2.name}
									(${flightTo.airport2.iataCode})
								</p>
								<p class="flight-time">
									${flightTo.departureTime} <span class="arrow">→</span>
									${flightTo.arrivalTime}
								</p>
							</div>
						</label>
					</c:forEach>

				</div>
				<form:input type="hidden" value="${numberOfSeats }"
					path="numberOfSeats" />
				<input type="submit" value="Book">
			</form:form>
		</c:otherwise>
	</c:choose>
	<c:if test="${!empty validation_error }">
		<div class="error-box">
			<h4>${validation_error}</h4>
			<hr class="error-separator">
			<c:forEach items="${errors}" var="error">
				<p>• ${error.defaultMessage}</p>
			</c:forEach>
		</div>
	</c:if>

</body>
</html>