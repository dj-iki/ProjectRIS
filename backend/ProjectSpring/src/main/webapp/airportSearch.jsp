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
	<c:forEach items="${airports }" var="airport">
		<p>${airport.name }</p>
		<a href="/ProjectRIS/search/flightsFromCountries?airportId=${airport.idAirport }"><button type="button">Search</button></a>
	</c:forEach>
</body>
</html>