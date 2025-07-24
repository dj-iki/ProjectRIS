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
	function togleForms() {
		const checked = document.getElementById("togleFormsCheckbox").checked;
		document.getElementById("form1").style.display = checked ? "none"
				: "block";
		document.getElementById("form2").style.display = checked ? "block"
				: "none";
	}
</script>
</head>
<body>

	<lable> Returning flight : <input type="checkbox"
		id="togleFormsCheckbox" onclick="togleForms()">
	<br>
	</lable>
	<div id="form1">
		<form:form action="/ProjectRIS/search/findFlights" method="get"
			modelAttribute="flightDTO">
			<form:select path="fromAirport">
				<c:forEach items="${airports }" var="airport">
					<form:option value="${airport.idAirport }">${airport.name } (${airport.iataCode})</form:option>
				</c:forEach>
			</form:select>
			<form:select path="toAirport">
				<form:option value="" label="-- Select arrival airport --" />
				<c:forEach items="${airports }" var="airport">
					<option value="${airport.idAirport }">${airport.name }
						(${airport.iataCode})</option>
				</c:forEach>
			</form:select>
			<form:input path="departureDate" type="date" />
			<input type="submit" value="Search">
		</form:form>
	</div>
	<div id="form2" style="display: none">
		<form:form action="/ProjectRIS/search/findFlights" method="get" modelAttribute="flightDTO">
			<form:select path="fromAirport">
				<c:forEach items="${airports }" var="airport">
					<form:option value="${airport.idAirport }">${airport.name } (${airport.iataCode})</form:option>
				</c:forEach>
			</form:select>
			<form:select path="toAirport">
				<form:option value="" label="-- Select arrival airport --" />
				<c:forEach items="${airports }" var="airport">
					<option value="${airport.idAirport }">${airport.name }
						(${airport.iataCode})</option>
				</c:forEach>
			</form:select>
			<form:input path="departureDate" type="date" />
			<form:input path="returningDate" type="date" />
			<input type="submit" value="Search">
		</form:form>
	</div>
	<c:if test="${!empty validation_error }">
		<h4>${validation_error }</h4>
		------------------------------------
		<c:forEach items="${errors}" var="error">
			<p>${error.defaultMessage }</p>
		</c:forEach>
	</c:if>
</body>
</html>