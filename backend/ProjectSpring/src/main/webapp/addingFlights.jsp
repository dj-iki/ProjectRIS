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
	href="${pageContext.request.contextPath}/style/addingFlight.css">
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
        <c:if test="${!empty successful_insertion }">
            <p class="success">${successful_insertion }</p>
        </c:if>
        <c:if test="${!empty unsuccessful_insertion }">
            <p class="error">${unsuccessful_insertion }</p>
        </c:if>
    </div>

    <div class="form-container">
        <form:form action="/ProjectRIS/manager/save-flight" method="post" modelAttribute="flightInsertionDTO">
            <label>Departure date and time:</label>
            <input type="datetime-local" name="departure" required>
            <label>Arrival date and time:</label>
            <input type="datetime-local" name="arrival" required>
			
			<label>Departure ariport:</label>
            <select name="fromAirport">
                <c:forEach items="${airports }" var="airport">
                    <option value="${airport.idAirport }">${airport.name } (${airport.iataCode })</option>
                </c:forEach>
            </select>

			<label>Arrival ariport:</label>
            <select name="toAirport">
                <c:forEach items="${airports }" var="airport">
                    <option value="${airport.idAirport }">${airport.name } (${airport.iataCode })</option>
                </c:forEach>
            </select>
			<label>Plane:</label>
            <select name="plane">
                <c:forEach items="${planes }" var="plane">
                    <option value="${plane.idPlane }">${plane.manufacturer} ${plane.model } ${plane.registrationNumber }</option>
                </c:forEach>
            </select>
            <div class="employee-checkboxes">
            	<label>Employees:</label>
                <c:forEach items="${employees }" var="employee">
                    <label><input type="checkbox" name="employees" value="${employee.idUsers }">${employee.name } ${employee.surname }</label>
                </c:forEach>
            </div>

            <input type="submit" value="Save">
        </form:form>
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