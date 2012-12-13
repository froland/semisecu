<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="navigation">
		<t:navigation.hotel />
	</jsp:attribute>
	<jsp:attribute name="title">
		<c:choose>
		<c:when test="${empty user.id}">
		New user
		</c:when>
		<c:otherwise>
		Updating user
		</c:otherwise>
		</c:choose>
	</jsp:attribute>
	<jsp:body>
		<form:form modelAttribute="user">
			<form:errors path="*" />
			<table>
				<tr>
					<td>Name</td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><form:password path="password" /></td>
				</tr>
				<tr>
					<td>Re-type your Password</td>
					<td><form:password path="retypedPassword" /></td>
				</tr>
				<tr>
					<td>E-mail</td>
					<td><form:input path="email" /></td>
				</tr>
				
			<tr>
					<td />
					<td>
					<c:choose>
					<c:when test="${empty user.id}">
					<input type="submit" value="Add user" />
					</c:when>
					<c:otherwise>
					<input type="submit" value="Update user" />
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</table>
		</form:form>
	</jsp:body>
</t:page.template>