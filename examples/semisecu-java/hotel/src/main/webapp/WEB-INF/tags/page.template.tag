<%-- based on http://stackoverflow.com/questions/10529963/what-is-the-best-way-to-create-jsp-layout-template --%>
<%@tag description="Main page template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@attribute name="title" type="java.lang.String"
	description="The title of the page"%>
<%@attribute name="navigation" fragment="true"
	description="Navigation toolbar"%>
<%@attribute name="head" fragment="true"
	description="Additional elements for the head"%>
<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<jsp:invoke fragment="head" />
</head>
<body>
	<div id="login">
		<sec:authorize access="isAuthenticated()">
			Authentified as <sec:authentication property="principal.username" />
			<a href="<c:url value="/logout"/>">Log out</a>
		</sec:authorize>
		<sec:authorize access="!isAuthenticated()">
			<a href="<c:url value="/login"/>">Log in</a>
		</sec:authorize>
	</div>
	<c:choose>
		<c:when test="${empty navigation}">
			<a href="<c:url value="/"/>">Return to home</a>
		</c:when>
		<c:otherwise>
			<div id="navigation">
				<jsp:invoke fragment="navigation" />
			</div>
		</c:otherwise>
	</c:choose>
	<h1>${title}</h1>
	<div id="body">
		<jsp:doBody />
	</div>
</body>
</html>