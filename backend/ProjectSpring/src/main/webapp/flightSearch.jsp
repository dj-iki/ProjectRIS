<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${!empty returning }">
			<div style="display: flex"> 
				<div style="padding: 10px">
					<c:forEach items="${flightsTo }" var="flightTo">
						<p>${flightTo.flightNumber }${flightTo.plane.airline.name }</p>
						<br>
						<p>${flightTo.airport1.name }(${flightTo.airport1.iataCode })
							----> ${flightTo.airport2.name } (${flightTo.airport2.iataCode })</p>
						<br>
						<p>${flightTo.departureTime }---->${flightTo.arrivalTime }
					</c:forEach>
				</div>
				<div style="padding: 10px">
					<c:forEach items="${returning }" var="flight">
						<p>${flight.flightNumber }${flight.plane.airline.name }</p>
						<br>
						<p>${flight.airport1.name }(${flight.airport1.iataCode })
							----> ${flight.airport2.name } (${flight.airport2.iataCode })</p>
						<br>
						<p>${flight.departureTime }---->${flight.arrivalTime }
					</c:forEach>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach items="${flightsTo }" var="flight">
				<p>${flight.flightNumber }${flight.plane.airline.name }</p>
				<br>
				<p>${flight.airport1.name }(${flight.airport1.iataCode })---->
					${flight.airport2.name } (${flight.airport2.iataCode })</p>
				<br>
				<p>${flight.departureTime }---->${flight.arrivalTime }
			</c:forEach>
		</c:otherwise>
	</c:choose>
</body>
</html>