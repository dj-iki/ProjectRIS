<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
			<input type="text" name="name" placeholder="${appUserDTO.name }" />
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displaySurname()">Update surname</button><br>
	<div id="surname"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updateSurname" modelAttribute="appUserDTO">
			<input type="hidden" name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<input type="text" name="surname" placeholder="${appUserDTO.surname }" />
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayEmail()">Update email</button><br>
	<div id="email"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updateEmail" modelAttribute="appUserDTO">
			<input type="hidden" name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<input type="text" name="email" placeholder="${appUserDTO.email }"/>
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayUsername()">Update username</button><br>
	<div id="username"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updateUsername" modelAttribute="appUserDTO">
			<input type="hidden" name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<input type="text" name="newUsername" placeholder="${appUserDTO.oldUsername }"/>
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayPassword()">Update password</button><br>
	<div id="password"  style="display: none">
		<form:form method="post" action="/ProjectRIS/account/updatePassword" modelAttribute="appUserDTO">
			<input type="hidden"  name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<form:input type="password" path="oldPassword"/><br>
			<form:input type="password" path="newPassword" />
			<input type="submit" value="Update">
		</form:form>
	</div>
	<button type="button" onclick="displayDelete()">Delete account</button><br>
	<div id="delete" style="display: none">
		<form:form method="post" action="/ProjectRIS/account/delete" modelAttribute="appUserDTO">
			<input type="hidden"  name="oldUsername" value="${appUserDTO.oldUsername }"/>
			<form:input type="password" path="oldPassword"/>
			<input type="submit" value="Delete">
		</form:form>
	</div>

</body>
</html>