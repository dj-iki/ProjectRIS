<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/delayFlight.css">
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
	<div class="message-box">
	<c:if test="${!empty successfull_delay}"><p class="success">${successfull_delay}</p></c:if>
	<c:if test="${!empty unsuccessfull_delay}"><p class="error">${unsuccessfull_delay}</p></c:if>
	<c:if test="${!empty successfull_cancel}"><p class="success">${successfull_cancel}</p></c:if>
	<c:if test="${!empty unsuccessfull_cancel}"><p class="error">${unsuccessfull_cancel}</p></c:if>
	</div>
	<c:if test="${!empty flights }">
		<div class="flights-container">
			<c:forEach items="${flights}" var="flight">
				<div class="flight-form">
					<form:form action="/ProjectRIS/manager/delay-flight" method="post"
						modelAttribute="flightDelayDTO">
						<form:input type="hidden" path="flightId" value="${flight.idFlight}" />
						<label>Delay departure time:</label>
						<input type="datetime-local" name="departure">
						<label>Delay arrival time:</label>
						<input type="datetime-local" name="arrival">
						<input type="submit" value="Save">
					</form:form>
					<form action="/ProjectRIS/manager/cancel-flight?flightId=${flight.idFlight}" method="post">
						<input type="submit" value="Cancel flight" class="cancel-button">
					</form>
				</div>
			</c:forEach>
		</div>
	</c:if>
</body>
</html>