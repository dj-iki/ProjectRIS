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
	<form:form action="/ProjectRIS/auth/register" method="post"
		modelAttribute="appUserRegisterDTO">

		<label>Username:</label>
		<form:input path="username" />
		<br>

		<label>Password:</label>
		<form:password path="password" />
		<br>

		<label>Email:</label>
		<form:input path="email" />
		<br>

		<label>Name:</label>
		<form:input path="name" />
		<br>

		<label>Surname:</label>
		<form:input path="surname" />

		<br>
		<label>Role:</label> 
		<form:select path="role">
			<form:options items="${roles }" itemValue="idRole" itemLabel="name" />
		</form:select>
		<br>
		<input type="submit" value="Register">
	</form:form>
	<c:if test="${!empty error_with_adding_user }">
		<p>${error_with_adding_user }</p>
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