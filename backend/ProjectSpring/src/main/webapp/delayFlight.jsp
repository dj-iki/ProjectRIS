<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${!empty successfull_delay }">
		${successfull_delay }
	</c:if>
	<c:if test="${!empty unsuccessfull_delay }">
		${unsuccessfull_delay }
	</c:if>
	<c:if test="${!empty successfull_cancel }">
		${successfull_cancel }
	</c:if>
	<c:if test="${!empty unsuccessfull_cancel }">
		${unsuccessfull_cancel }
	</c:if>
	<c:if test="${!empty flights }">
		<c:forEach items="${flights }" var="flight">
			<form:form action="/ProjectRIS/manager/delay-flight" method="post" modelAttribute="flightDelayDTO">
				<form:input type="hidden" path="flightId" value="${flight.idFlight }" />
				<input type="datetime-local" name="departure"><br>
				<input type="datetime-local" name="arrival"><br>
				<input type="submit" value="Save">
			</form:form>
			<form action="/ProjectRIS/manager/cancel-flight?flightId=${flight.idFlight }" method="post">
				<input type="submit" value="Cancel flight">
			</form>
		</c:forEach>
	</c:if>
	 
</body>
</html>