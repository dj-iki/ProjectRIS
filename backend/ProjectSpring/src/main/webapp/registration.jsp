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
	href="${pageContext.request.contextPath}/style/registration.css">
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
	<div class="form-container">
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
			
			<form:input type="hidden" path="role" value="1" />
			<input type="submit" value="Register">
		</form:form>
	</div>
	<c:if test="${!empty error_with_adding_user }">
		<p class="error-message">${error_with_adding_user }</p>
	</c:if>
	<c:if test="${!empty validation_error }">
		<div class="error-box">
			<h4>${validation_error}</h4>
			<hr class="error-separator">
			<c:forEach items="${errors}" var="error">
				<p>${error.defaultMessage}</p>
			</c:forEach>
		</div>
	</c:if>
</body>
</html>