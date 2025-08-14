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
	href="${pageContext.request.contextPath}/style/admin.css">
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
	<c:if test="${!empty adding_country }">
		<div class="messsage-box">${adding_country }</div>
	</c:if>
	<c:if test="${!empty adding_city }">
		<div class="messsage-box">${adding_city }</div>
	</c:if>
	<c:if test="${!empty adding_airport }">
		<div class="messsage-box">${adding_airport }</div>
	</c:if>
	<c:if test="${!empty adding_airline }">
		<div class="messsage-box">${adding_airline }</div>
	</c:if>
	<c:if test="${!empty promote_employee }">
		<div class="messsage-box">${promote_employee }</div>
	</c:if>
	
	<form action="/ProjectRIS/admin/save-country" method="post" >
		<label>Insert country name:</label>
		<input type="text" name="country_name" required pattern="[a-zA-Z- ]">
		<input type="submit" value="Save">
	</form>
	
	<form action="/ProjectRIS/admin/save-city" method="post" >
		<label>Insert city name:</label>	
		<input type="text" name="city_name" required pattern="[a-zA-Z- ]">
		<label>Select country:</label><select name="country" required>
			<c:forEach items="${countries }" var="c">
				<option value="${c.idCountry }">${c.name }</option>
			</c:forEach>
		</select>
		<input type="submit" value="Save">
	</form>
	
	<form:form action="/ProjectRIS/admin/save-airport" method="post" modelAttribute="airportDTO">
		<label>Insert airport name:</label>
		<form:input type="text" path="name" />
		<label>Insert airport iata code:</label>
		<form:input type="text" path="iataCode" />
		<label>Insert airport icao code:</label>
		<form:input type="text" path="icaoCode" />
		<label>Select city:</label><form:select path="cityId">
			<form:options items="${cities }" itemLabel="name" itemValue="idCity"/>
		</form:select>
		<input type="submit" value="Save">
	</form:form>
	
	<form:form action="/ProjectRIS/admin/save-airline" method="post" modelAttribute="airlineDTO">
		<label>Insert airline name:</label>
		<form:input type="text" path="name" />
		<label>Insert airline iata code:</label>
		<form:input type="text" path="iataCode" />
		<label>Insert airline icao code:</label>
		<form:input type="text" path="icaoCode" />
		<input type="submit" value="Save">
	</form:form>
	
	<form action="/ProjectRIS/admin/promote-employee" method="post">
		
		<label>Insert employee username:</label>
		<input type="text" name="username" required>
		<label>Select airline:</label><select name="airline" required>
			<c:forEach items="${airlines }" var="a">
				<option value="${a.idAirlines }">${a.name }</option>
			</c:forEach>
		</select>
		<input type="submit" value="Promote">
	</form>
	
	<c:if test="${!empty validation_error_airport }">
		<div class="error-box">
			<h4>${validation_error_aiport}</h4>
			<hr class="error-separator">
			<c:forEach items="${errors}" var="error">
				<p>${error.defaultMessage}</p>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${!empty validation_error_airline }">
		<div class="error-box">
			<h4>${validation_error_ailine}</h4>
			<hr class="error-separator">
			<c:forEach items="${errors}" var="error">
				<p>${error.defaultMessage}</p>
			</c:forEach>
		</div>
	</c:if>
</body>
</html>