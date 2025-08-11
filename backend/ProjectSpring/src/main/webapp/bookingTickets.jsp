<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
  document.addEventListener("DOMContentLoaded", function () {
    const numberOfSeats = parseInt("${bookingDTO.numberOfSeats}", 10);

    for (let i = 0; i < numberOfSeats; i++) {
      const fromName = document.getElementById("from-name-" + i);
      const fromSurname = document.getElementById("from-surname-" + i);
      const fromPassport = document.getElementById("from-passportNumber-" + i);

      const returnName = document.getElementById("returning-name-" + i);
      const returnSurname = document.getElementById("returning-surname-" + i);
      const returnPassport = document.getElementById("returning-passportNumber-" + i);

      if (fromName && returnName) {
        fromName.addEventListener("input", () => returnName.value = fromName.value);
      }
      if (fromSurname && returnSurname) {
        fromSurname.addEventListener("input", () => returnSurname.value = fromSurname.value);
      }
      if (fromPassport && returnPassport) {
        fromPassport.addEventListener("input", () => returnPassport.value = fromPassport.value);
      }
    }
  });
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/bookings.css">

</head>
<body>
	<c:if test="${!empty error_with_booking}">
		<p class="error-message">${error_with_booking}</p>
	</c:if>

	<c:choose>
		<c:when test="${bookingDTO.flightIdReturning != null}">
			<form:form action="/ProjectRIS/booking/save" method="post"
				modelAttribute="bookingDTO">
				<form:input type="hidden" value="${bookingDTO.flightIdFrom}"
					path="flightIdFrom" />
				<form:input type="hidden" value="${bookingDTO.flightIdReturning}"
					path="flightIdReturning" />
				<form:input type="hidden" value="${bookingDTO.numberOfSeats}"
					path="numberOfSeats" />

				<div class="ticket-container">
					<div>
						<c:forEach var="i" begin="0" end="${bookingDTO.numberOfSeats - 1}">
							<fieldset class="ticket-card">
								<legend>Ticket ${i + 1}</legend>
								<form:input type="text" path="ticketsFromDTO[${i}].name"
									placeholder="Name" id="from-name-${i }"/>
								<form:input type="text" path="ticketsFromDTO[${i}].surname"
									placeholder="Surname" id="from-surname-${i }" />
								<form:input type="text"
									path="ticketsFromDTO[${i}].passportNumber"
									placeholder="Passport Number" id="from-passportNumber-${i }"/>
								<form:select path="ticketsFromDTO[${i}].seatId">
									<form:options items="${seatsFrom}" itemValue="idSeat"
										itemLabel="seatNumber" />
								</form:select>
								<div class="baggage-options">
									<label><form:radiobutton
											path="ticketsFromDTO[${i}].baggage" value="CARRY_ON"/>Carry-on</label>
									<label><form:radiobutton
											path="ticketsFromDTO[${i}].baggage" value="CHECKED"/>Checked</label>
								</div>
							</fieldset>
						</c:forEach>
					</div>

					<div>
						<c:forEach var="i" begin="0" end="${bookingDTO.numberOfSeats - 1}">
							<fieldset class="ticket-card">
								<legend>Ticket ${i + 1}</legend>
								<form:input type="text" path="ticketsReturningDTO[${i}].name"
									placeholder="Name" id="returning-name-${i }" />
								<form:input type="text" path="ticketsReturningDTO[${i}].surname"
									placeholder="Surname" id="returning-surname-${i }"/>
								
								<form:input type="text"
									path="ticketsReturningDTO[${i}].passportNumber"
									placeholder="Passport Number" id="returning-passportNumber-${i }" />
								<form:select path="ticketsReturningDTO[${i}].seatId">
									<form:options items="${seatsReturning}" itemValue="idSeat"
										itemLabel="seatNumber" />
								</form:select>
								<div class="baggage-options">
									<label><form:radiobutton path="ticketsReturningDTO[${i}].baggage" value="CARRY_ON"/>Carry-on</label>
									<label><form:radiobutton path="ticketsReturningDTO[${i}].baggage" value="CHECKED"/> Checked</label>
								</div>
							</fieldset>
						</c:forEach>
					</div>
				</div>

				<input class="btn-submit" type="submit" value="Book">
			</form:form>
		</c:when>

		<c:otherwise>
			<form:form action="/ProjectRIS/booking/save" method="post"
				modelAttribute="bookingDTO">
				<form:input type="hidden" value="${bookingDTO.flightIdFrom}"
					path="flightIdFrom" />
				<form:input type="hidden" value="${bookingDTO.flightIdReturning}"
					path="flightIdReturning" />
				<form:input type="hidden" value="${bookingDTO.numberOfSeats}"
					path="numberOfSeats" />

				<c:forEach var="i" begin="0" end="${bookingDTO.numberOfSeats - 1}">
					<fieldset class="ticket-card">
						<legend>Ticket ${i + 1}</legend>
						<form:input type="text" path="ticketsFromDTO[${i}].name"
							placeholder="Name" />
						<form:input type="text" path="ticketsFromDTO[${i}].surname"
							placeholder="Surname"/>
						<form:input type="text" path="ticketsFromDTO[${i}].passportNumber"
							placeholder="Passport Number" />
						<form:select path="ticketsFromDTO[${i}].seatId">
							<form:options items="${seatsFrom}" itemValue="idSeat"
								itemLabel="seatNumber" />
						</form:select>
						<div class="baggage-options">
							<label><form:radiobutton path="ticketsFromDTO[${i}].baggage" value="CARRY_ON"/> Carry-on</label> 
							<label><form:radiobutton path="ticketsFromDTO[${i}].baggage" value="CHECKED"/> Checked</label>
						</div>
					</fieldset>
				</c:forEach>

				<input class="btn-submit" type="submit" value="Book">
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