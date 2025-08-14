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
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/index.css">
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
	
	<div class="search-block">
		<div class="returning-checkbox">
		<label> Returning flight : <input type="checkbox"
			id="togleFormsCheckbox" onclick="togleForms()"> 
		</label>
		</div>
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
				<form:input path="numberOfSeats" type="number" />
				<input type="submit" value="Search">
			</form:form>
		</div>
		<div id="form2" style="display: none">
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
				<form:input path="returningDate" type="date" />
				<form:input path="numberOfSeats" type="number" />
				<input type="submit" value="Search">
			</form:form>
		</div>
	</div>
	<c:choose>
		<c:when test="${!empty no_one_way_flights}">
			<div class="result-message">${no_one_way_flights}</div>
		</c:when>
		<c:when test="${!empty no_returning_flights}">
			<div class="result-message">${no_returning_flights}</div>
		</c:when>
		<c:when test="${!empty no_one_way_countries}">
			<div class="result-message">${no_one_way_countries}</div>
		</c:when>
		<c:when test="${!empty no_returning_countries}">
			<div class="result-message">${no_returning_countries}</div>
		</c:when>
		<c:when test="${!empty successfull_booking}">
			<div class="result-message">${successfull_booking}</div>
		</c:when>
	</c:choose>
	<c:if test="${!empty validation_error }">
		<div class="error-box">
			<h4>${validation_error}</h4>
			<hr class="error-separator">
			<c:forEach items="${errors}" var="error">
				<p>${error.defaultMessage}</p>
			</c:forEach>
		</div>
	</c:if>
</body>
</html>