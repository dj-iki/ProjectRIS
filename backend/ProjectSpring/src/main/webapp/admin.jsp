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
	<c:if test="${!empty adding_country }">
		${adding_country }
	</c:if>
	<c:if test="${!empty adding_city }">
		${adding_city }
	</c:if>
	<c:if test="${!empty adding_airport }">
		${adding_airport }
	</c:if>
	<c:if test="${!empty adding_airline }">
		${adding_airline }
	</c:if>
	<c:if test="${!empty promote_employee }">
		${promote_employee }
	</c:if>
	
	<form action="/ProjectRIS/admin/save-country" method="post" >
		<input type="text" name="name" required pattern="[a-zA-Z- ]">
		<input type="submit" value="Save">
	</form>
	
	<form action="/ProjectRIS/admin/save-city" method="post" >
		<input type="text" name="city_name" required pattern="[a-zA-Z- ]">
		<select name="country" required>
			<c:forEach items="${countries }" var="c">
				<option value="${c.idCountry }">${c.name }</option>
			</c:forEach>
		</select>
		<input type="submit" value="Save">
	</form>
	
	<form:form action="/ProjectRIS/admin/save-airport" method="post" modelAttribute="airportDTO">
		<form:input type="text" path="name" />
		<form:input type="text" path="iataCode" />
		<form:input type="text" path="icaoCode" />
		<form:select path="cityId">
			<form:options items="${cities }" itemLabel="name" itemValue="idCity"/>
		</form:select>
		<input type="submit" value="Save">
	</form:form>
	
	<form:form action="/ProjectRIS/admin/save-airline" method="post" modelAttribute="airlineDTO">
		<form:input type="text" path="name" />
		<form:input type="text" path="iataCode" />
		<form:input type="text" path="icaoCode" />
		<input type="submit" value="Save">
	</form:form>
	
	<form action="/ProjectRIS/admin/promote-employee" method="post">
		<input type="text" name="username" required>
		<select name="airline" required>
			<c:forEach items="${airlines }" var="a">
				<option value="${a.idAirlines }">${a.name }</option>
			</c:forEach>
		</select>
		<input type="submit" value="Promote">
	</form>
	
	
	<c:if test="${!empty validation_error_airport }">
		<h4>${validation_error_airport }</h4>
		------------------------------------
		<c:forEach items="${errors_airport}" var="error">
			<p>${error.defaultMessage }</p>
		</c:forEach>
	</c:if>
	
	<c:if test="${!empty validation_error_airline }">
		<h4>${validation_error_airline }</h4>
		------------------------------------
		<c:forEach items="${errors_airline}" var="error">
			<p>${error.defaultMessage }</p>
		</c:forEach>
	</c:if>
</body>
</html>