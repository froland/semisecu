<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="title">User: ${user.name}</jsp:attribute>
	<jsp:body>
		<div>Name: ${user.name}</div>
		<div>Email: ${user.email}</div>
		<div><a href="<c:url value="/user/update/${user.id}"/>" class="btn btn-primary">Update profile</a></div>
	</jsp:body>
</t:page.template>