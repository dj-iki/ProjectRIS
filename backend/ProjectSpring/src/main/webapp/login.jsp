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
	<c:if test="${!empty successful_registration }">
		<p>${successful_registration }</p>
	</c:if>
	<c:if test="${!empty successfull_update }">
		<p>${successfull_update }</p>
	</c:if>
	<form action="/ProjectRIS/auth/login" method="post">
		<input type="text" name="username">
		<input type="password" name="password">
		<input type="submit" value="Login">
	</form>
	<c:if test="${!empty exception }">
		<p>${exception }</p>
	</c:if>
	<c:if test="${!empty validation_error }">
		<h4>${validation_error }</h4>
		------------------------------------
		<c:forEach items="${errors}" var="error">
			<p>${error.defaultMessage }</p>
		</c:forEach>
	</c:if>
</body>
</html>