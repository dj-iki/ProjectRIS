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
	href="${pageContext.request.contextPath}/style/updateAccount.css">
<script type="text/javascript">
	function displayName(){
		const div = document.getElementById('name');
		if (div.style.display === 'none' || div.style.display === ''){
			div.style.display = 'block';
		}
		else{
			div.style.display = 'none';
		}
	}
	function displaySurname(){
		const div = document.getElementById('surname');
		if (div.style.display === 'none' || div.style.display === ''){
			div.style.display = 'block';
		}
		else{
			div.style.display = 'none';
		}
	}
	function displayEmail(){
		const div = document.getElementById('email');
		if (div.style.display === 'none' || div.style.display === ''){
			div.style.display = 'block';
		}
		else{
			div.style.display = 'none';
		}
	}
	function displayUsername(){
		const div = document.getElementById('username');
		if (div.style.display === 'none' || div.style.display === ''){
			div.style.display = 'block';
		}
		else{
			div.style.display = 'none';
		}
	}
	function displayPassword(){
		const div = document.getElementById('password');
		if (div.style.display === 'none' || div.style.display === ''){
			div.style.display = 'block';
		}
		else{
			div.style.display = 'none';
		}
	}
	function displayDelete(){
		const div = document.getElementById('delete');
		if (div.style.display === 'none' || div.style.display === ''){
			div.style.display = 'block';
		}
		else{
			div.style.display = 'none';
		}
	}
</script>
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
	
	<c:if test="${!empty successfull_update }">
		${successfull_update }<br>
	</c:if>
	<c:if test="${!empty unsuccessfull_update }">
		${unsuccessfull_update }<br>
	</c:if>
	
	<button type="button" onclick="displayName()">Update name</button><br>
	<div id="name" style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updateName" modelAttribute="appUserDTO">
			<input type="hidden" name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<label>New name:</label>
			<input type="text" name="name" placeholder="${appUserDTO.name }" required pattern="[a-zA-Z ]{2,100}"/>
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displaySurname()">Update surname</button><br>
	<div id="surname"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updateSurname" modelAttribute="appUserDTO">
			<input type="hidden" name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<label>New surname:</label>
			<input type="text" name="surname" placeholder="${appUserDTO.surname }" required pattern="[a-zA-Z ]{2,100}" />
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayEmail()">Update email</button><br>
	<div id="email"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updateEmail" modelAttribute="appUserDTO">
			<input type="hidden" name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<label>New email:</label>
			<input type="text" name="email" placeholder="${appUserDTO.email }" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}"/>
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayUsername()">Update username</button><br>
	<div id="username"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updateUsername" modelAttribute="appUserDTO">
			<input type="hidden" name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<label>New username:</label>
			<input type="text" name="newUsername" placeholder="${appUserDTO.oldUsername }" required pattern=".{4,16}"/>
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayPassword()">Update password</button><br>
	<div id="password"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updatePassword" modelAttribute="appUserDTO">
			<input type="hidden"  name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<label>Old password:</label>
			<form:input type="password" path="oldPassword" required="required"/><br>
			<label>New password:</label>
			<form:input type="password" path="newPassword" required="required" pattern="[a-zA-Z0-9@#$%^&+=!_-]+"/>
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayDelete()">Delete account</button><br>
	<div id="delete" style="display: none">
		<form:form method="post" action="/ProjectRIS/account/delete" modelAttribute="appUserDTO">
			<input type="hidden"  name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<label>Input password to delete account:</label>
			<form:input type="password" path="oldPassword" required="required"/>
			<input type="submit" value="Delete">
		</form:form>
	</div>

</body>
</html>