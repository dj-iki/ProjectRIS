<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Plane</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/style/addingPlane.css">
</head>
<body>
    <div class="nav-buttons">
        <a href="/ProjectRIS/"><button>Home</button></a>
        <sec:authorize access="!isAuthenticated()">
            <a href="/ProjectRIS/auth/redirect-login"><button type="button">Log in</button></a>
            <a href="/ProjectRIS/auth/redirect-register"><button type="button">Register</button></a>
        </sec:authorize>

        <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_EMPLOYEE','ROLE_MANAGER','ROLE_ADMIN')">
            <a href="/ProjectRIS/account/redirect"><button>Account</button></a>
            <a href="/ProjectRIS/auth/logout"><button>Log out</button></a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_MANAGER')">
            <a href="/ProjectRIS/manager/redirect-addFlight"><button>Add Flight</button></a>
            <a href="/ProjectRIS/manager/redirect-addPlane"><button>Add Plane</button></a>
            <a href="/ProjectRIS/manager/redirect-hire"><button>Hire User</button></a>
            <a href="/ProjectRIS/manager/redirect-delay"><button>Delay/Cancel Flight</button></a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="/ProjectRIS/admin/redirect"><button>Admin Functionalities</button></a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_EMPLOYEE')">
            <a href="/ProjectRIS/employee/flights"><button>See your flights</button></a>
        </sec:authorize>
    </div>

    <!-- Success / Error messages -->
    <c:if test="${!empty successful_insertion }">
        <div class="success-box">${successful_insertion}</div>
    </c:if>
    <c:if test="${!empty unsuccessful_insertion }">
        <div class="error-box">${unsuccessful_insertion}</div>
    </c:if>

    <!-- Plane form -->
    <div class="form-wrapper">
        <form:form action="/ProjectRIS/manager/save-plane" method="post" modelAttribute="planeDTO" cssClass="plane-form">
            <div class="form-grid">
                <div class="form-row">
                    <form:label path="registrationNumber">Registration number</form:label>
                    <form:input path="registrationNumber" />
                </div>

                <div class="form-row">
                    <form:label path="manufacturer">Manufacturer</form:label>
                    <form:input path="manufacturer" />
                </div>

                <div class="form-row">
                    <form:label path="model">Model</form:label>
                    <form:input path="model" />
                </div>

                <div class="form-row">
                    <form:label path="numberOfEconomySeats">Economy seats</form:label>
                    <form:input path="numberOfEconomySeats" type="number" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="economyPrice">Economy price</form:label>
                    <form:input path="economyPrice" type="number" step="0.01" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="numberOfEconomyRows">Economy rows</form:label>
                    <form:input path="numberOfEconomyRows" type="number" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="numberOfEconomyPlusSeats">Economy Plus seats</form:label>
                    <form:input path="numberOfEconomyPlusSeats" type="number" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="economyPlusPrice">Economy Plus price</form:label>
                    <form:input path="economyPlusPrice" type="number" step="0.01" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="numberOfEconomyPlusRows">Economy Plus rows</form:label>
                    <form:input path="numberOfEconomyPlusRows" type="number" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="numberOfBusinessSeats">Business seats</form:label>
                    <form:input path="numberOfBusinessSeats" type="number" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="businessPrice">Business price</form:label>
                    <form:input path="businessPrice" type="number" step="0.01" min="0" />
                </div>

                <div class="form-row">
                    <form:label path="numberOfBusinessRows">Business rows</form:label>
                    <form:input path="numberOfBusinessRows" type="number" min="0" />
                </div>
            </div>

            <div class="form-actions">
                <input type="submit" value="Save" class="btn-save" />
            </div>
        </form:form>

        <!-- Validation errors -->
        <c:if test="${!empty validation_error }">
		<div class="error-box">
			<h4>${validation_error}</h4>
			<hr class="error-separator">
			<c:forEach items="${errors}" var="error">
				<p>${error.defaultMessage}</p>
			</c:forEach>
		</div>
	</c:if>
    </div>

</body>
</html>
