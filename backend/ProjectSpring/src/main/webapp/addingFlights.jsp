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
	<c:if test="${!empty successful_insertion }">
		${successful_insertion }
	</c:if>
	<c:if test="${!empty unsuccessful_insertion }">
		${unsuccessful_insertion }
	</c:if>
	<form:form action="/ProjectRIS/manager/save-flight" method="post" modelAttribute="flightInsertionDTO">
		    <input type="datetime-local" name="departure">
		    <input type="datetime-local" name="arrival">
		    <select name="fromAirport">
		    	<c:forEach items="${airports }" var="airport">
		    		<option value="${airport.idAirport }">${airport.name } (${airport.iataCode }) </option>
		    	</c:forEach>
		    </select>
		    <select name="toAirport">
		    	<c:forEach items="${airports }" var="airport">
		    		<option value="${airport.idAirport }">${airport.name } (${airport.iataCode }) </option>
		    	</c:forEach>
		    </select>
		    <select name="plane">
		    	<c:forEach items="${planes }" var="plane">
		    		<option value="${plane.idPlane }">${plane.manufacturer} ${plane.model } ${plane.registrationNumber } </option>
		    	</c:forEach>
		    </select>
		    <c:forEach items="${employees }" var="employee">
		    	<label>${employee.name } ${employee.surname }</label>
		    	<input type="checkbox" name="employees" value="${employee.idUsers }">
		    </c:forEach>
		    <input type="submit" value="Save">
    </form:form>
    <c:if test="${!empty validation_error }">
		<h4>${validation_error }</h4>
		------------------------------------
		<c:forEach items="${errors}" var="error">
			<p>${error.defaultMessage }</p>
		</c:forEach>
	</c:if>
</body>
</html>