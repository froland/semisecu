<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page.template>
	<jsp:attribute name="title">Login</jsp:attribute>
	<jsp:attribute name="head">
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
	</jsp:attribute>
	<jsp:body>
<%-- <body onload='document.loginForm.j_username.focus();'> --%>

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
	</jsp:body>
</t:page.template>