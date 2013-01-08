<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="title">User: ${user.name}</jsp:attribute>
	
		<jsp:attribute name="navigation" >
		
		<sec:authorize access= "hasRole('ADMIN')" > <t:navigation.admin /> </sec:authorize>
		<sec:authorize access= "!hasRole('ADMIN')" > <t:navigation.default /> </sec:authorize>
		
		
		
	</jsp:attribute>
	<jsp:body>
		<c:if test="${!user.enabled}">
		<p class="alert">The account is disabled.</p>
		</c:if>
		<p>Email: <a href="mailto:${user.email}"><c:out
					value="${user.email}" /></a>
		</p>
		<p>Roles: <t:list items="${user.roles}" />
		</p>
		<div>
			<a href="<c:url value="/user/update/${user.id}"/>" class="btn"><i
				class="icon-edit"></i> Update profile</a>
			<sec:authorize access="hasRole('ADMIN')">
			<form action="<c:url value="/user/enable/${user.id}"/>" method="post">
			<c:choose>
			<c:when test="${user.enabled}">
			<input type="hidden" name="enable" value="false" />
			<input type="submit" class="btn btn-warning" value="Disable account" />
			</c:when>
			<c:otherwise>
			<input type="hidden" name="enable" value="true" />
			<input type="submit" class="btn btn-warning" value="Enable account" />
			</c:otherwise>
			</c:choose>
			</form>
			</sec:authorize>
		</div>
	</jsp:body>
</t:page.template>