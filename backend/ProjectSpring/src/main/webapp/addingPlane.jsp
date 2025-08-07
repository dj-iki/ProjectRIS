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
	<form:form action="/ProjectRIS/manager/save-plane" method="post" modelAttribute="planeDTO">
		<label>registrationNumber</label>
		<input name="registrationNumber" type="text"><br>
		<label>manufacturer</label>
		<input name="manufacturer" type="text"><br>
		<label>model</label>
		<input name="model" type="text"><br>
		<label>numberOfEconomySeats</label>
		<input name="numberOfEconomySeats" type="number"><br>
		<label>economyPrice</label>
		<input name="economyPrice" type="number"><br>
		<label>numberOfEconomyRows</label>
		<input name="numberOfEconomyRows" type="number"><br>
		<label>numberOfEconomyPlusSeats</label>
		<input name="numberOfEconomyPlusSeats" type="number"><br>
		<label>economyPlusPrice</label>
		<input name="economyPlusPrice" type="number"><br>
		<label>numberOfEconomyPlusRows</label>
		<input name="numberOfEconomyPlusRows" type="number"><br>
		<label>numberOfBuisinessSeats</label>
		<input name="numberOfBusinessSeats" type="number"><br>
		<label>buisinessPrice</label>
		<input name="businessPrice" type="number"><br>
		<label>numberOfBuisinessRows</label>
		<input name="numberOfBusinessRows" type="number"><br>
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