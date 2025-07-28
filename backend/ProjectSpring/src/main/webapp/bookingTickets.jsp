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
	<c:if test="${!empty error_with_booking }">
		<p>${error_with_booking }</p>
	</c:if>
	<c:choose>
		<c:when test="${bookingDTO.flightIdReturning != null }">
			<form:form action="/ProjectRIS/booking/save" method="post" modelAttribute="bookingDTO">
				<form:input type="hidden" value="${bookingDTO.flightIdFrom }" path="flightIdFrom"/>
				<form:input type="hidden" value="${bookingDTO.flightIdReturning }" path="flightIdReturning" />
				<form:input type="hidden" value="${bookingDTO.numberOfSeats}" path="numberOfSeats"/>
				<div style="display: flex">
					<div>
						<c:forEach var="i" begin="0" end="${bookingDTO.numberOfSeats - 1 }">
							<fieldset>
								<legend>Ticket ${i + 1 }</legend>
								<form:input type="text" path="ticketsFromDTO[${i }].name" placeholder="Name"/>
								<form:input type="text" path="ticketsFromDTO[${i }].surname"/>
								<form:input type="text" path="ticketsFromDTO[${i }].passportNumber"/>
								<form:select path="ticketsFromDTO[${i }].seatId" >
									<form:options items="${seatsFrom }" itemValue="idSeat" itemLabel="seatNumber"/>
								</form:select>
								<form:radiobutton path="ticketsFromDTO[${i }].baggage" value="CARRY_ON"/>
								<form:radiobutton path="ticketsFromDTO[${i }].baggage" value="CHECKED"/>
							</fieldset>
						</c:forEach>
					</div>
					<div>
						<c:forEach var="i" begin="0" end="${bookingDTO.numberOfSeats - 1 }">
							<fieldset>
								<legend>Ticket ${i + 1 }</legend>
								<form:input type="text" path="ticketsReturningDTO[${i }].name"/>
								<form:input type="text" path="ticketsReturningDTO[${i }].surname"/>
								<form:input type="text" path="ticketsReturningDTO[${i }].passportNumber"/>
								<form:select path="ticketsReturningDTO[${i }].seatId" >
									<form:options items="${seatsReturning }" itemValue="idSeat" itemLabel="seatNumber"/>
								</form:select>
								<form:radiobutton path="ticketsReturningDTO[${i }].baggage" value="CARRY_ON"/>
								<form:radiobutton path="ticketsReturningDTO[${i }].baggage" value="CHECKED"/>
							</fieldset>
						</c:forEach>
					</div>
				</div>
				<input type="submit" value="Book">
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form action="/ProjectRIS/booking/save" method="post" modelAttribute="bookingDTO">
				<form:input type="hidden" value="${bookingDTO.flightIdFrom }" path="flightIdFrom" />
				<form:input type="hidden" value="${bookingDTO.flightIdReturning }" path="flightIdReturning" />
				<form:input type="hidden" value="${bookingDTO.numberOfSeats}" path="numberOfSeats"/>
				<c:forEach var="i" begin="0" end="${bookingDTO.numberOfSeats - 1 }">
						<fieldset>
							<legend>Ticket ${i + 1 }</legend>
							<form:input type="text" path="ticketsFromDTO[${i }].name"/>
							<form:input type="text" path="ticketsFromDTO[${i }].surname"/>
							<form:input type="text" path="ticketsFromDTO[${i }].passportNumber"/>
							<form:select path="ticketsFromDTO[${i }].seatId" >
								<form:options items="${seatsFrom }" itemValue="idSeat" itemLabel="seatNumber"/>
							</form:select>
							<form:radiobutton path="ticketsFromDTO[${i }].baggage" value="CARRY_ON"/>
							<form:radiobutton path="ticketsFromDTO[${i }].baggage" value="CHECKED"/>
						</fieldset>
				</c:forEach>
				<input type="submit" value="Book">
			</form:form>
		</c:otherwise>
	</c:choose>
</body>
</html>