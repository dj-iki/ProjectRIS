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
	<form:form action="/ProjectRIS/search/findFlights" method="get" modelAttribute="flightDTO">
		<form:select path="fromAirport">
			<c:forEach items="${airports }" var="airport">
				<form:option value="${airport.idAirport }">${airport.name } (${airport.iataCode})</form:option>
			</c:forEach>
		</form:select>
		<form:select path="toAirport">
			<c:forEach items="${airports }" var="airport">
				<option value="${airport.idAirport }">${airport.name } (${airport.iataCode})</option>
			</c:forEach>
		</form:select>
		<form:input path="departureDate" type="date"/>
		<form:input path="returningDate" type="date"/>
		<input type="submit" value="Search">
	</form:form>
</body>
</html>