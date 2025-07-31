<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${!empty returning }">
			<form:form action="/ProjectRIS/booking/new" method="get" modelAttribute="bookingDTO">
				<div style="display: flex">
					<div style="padding: 10px">
						<c:forEach items="${flightsTo }" var="flightTo">
							<p>${flightTo.flightNumber }${flightTo.plane.airline.name }</p>
							<br>
							<p>${flightTo.airport1.name }(${flightTo.airport1.iataCode })
								----> ${flightTo.airport2.name } (${flightTo.airport2.iataCode })</p>
							<br>
							<p>${flightTo.departureTime }---->${flightTo.arrivalTime }</p>
							<form:radiobutton path="flightIdFrom" value="${flightTo.idFlight }"/>
						</c:forEach>
					</div>
					<div style="padding: 10px">
						<c:forEach items="${returning }" var="flight">
							<p>${flight.flightNumber }${flight.plane.airline.name }</p>
							<br>
							<p>${flight.airport1.name }(${flight.airport1.iataCode })
								----> ${flight.airport2.name } (${flight.airport2.iataCode })</p>
							<br>
							<p>${flight.departureTime }---->${flight.arrivalTime }</p>
							<form:radiobutton path="flightIdReturning" value="${flight.idFlight }"/>
						</c:forEach>
					</div>
				</div>
				<form:input type="hidden" value="${flights }" path="numberOfSeats"/> 
				<input type="submit" value="Book">
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form action="/ProjectRIS/booking/new" method="get" modelAttribute="bookingDTO">
				<c:forEach items="${flightsTo }" var="flight">
					<p>${flight.flightNumber }${flight.plane.airline.name }</p>
					<br>
					<p>${flight.airport1.name }(${flight.airport1.iataCode })---->
						${flight.airport2.name } (${flight.airport2.iataCode })</p>
					<br>
					<p>${flight.departureTime }---->${flight.arrivalTime }</p>
					<form:radiobutton path="flightIdFrom" value="${flight.idFlight }"/>
				</c:forEach>
				<form:input type="hidden" value="${numberOfSeats }" path="numberOfSeats"/>
				<input type="submit" value="Book">
			</form:form>
		</c:otherwise>
	</c:choose>
</body>
</html>