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
	<form action="/ProjectRIS/admin/save-country" method="post" >
		<input type="text" name="name" />
		<input type="submit" value="Save">
	</form>
	
	<form action="/ProjectRIS/admin/save-city" method="post" >
		<input type="text" name="city_name" />
		<select name="country">
			<c:forEach items="${countries }" var="c">
				<option value="${c.idCountry }">${c.name }</option>
			</c:forEach>
		</select>
		<input type="submit" value="Save">
	</form>
	
	<form:form action="/ProjectRIS/admin/save-airport" method="post" modelAttribute="airportDTO">
		<form:input type="text" path="name" />
		<form:input type="text" path="iata_code" />
		<form:input type="text" path="icao_code" />
		<form:select path="cityId">
			<form:options items="${cities }" itemLabel="name" itemValue="idCity"/>
		</form:select>
		<input type="submit" value="Save">
	</form:form>
	
</body>
</html>