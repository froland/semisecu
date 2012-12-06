<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login</title>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body onload='document.loginForm.j_username.focus();'>
	<h1>Login</h1>

	<c:if test="${not empty param.error}">
		<div class="errorblock">
			<p>Your login attempt was not successful, try again.</p>
			Cause:
			<c:out
				value='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}' />
		</div>
	</c:if>

	<form name='loginForm' action="<c:url value='logincheck' />"
		method='POST'>
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Log in" /></td>
			</tr>
		</table>

	</form>
</body>
</html>
