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
<link href="<c:url value="/css/bootstrap.css"/>" type="text/css" rel="stylesheet"/>
<jsp:invoke fragment="head" />
</head>
<body>
	<div class="row-fluid">
		<div class="span4">
			<h1>OwaspHotel</h1>
		</div>
		<div class="span6 offset2">
			<div id="login">
				<sec:authorize access="isAuthenticated()">
					<div class="btn-group">
						<a class="btn btn-primary"
							href="<c:url value="/user/${pageContext['request'].userPrincipal.name}"/>"><i
							class="icon-user icon-white"></i> <sec:authentication
								property="principal.username" /></a> <a
							class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
							href="#"><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a
								href="<c:url value="/user/${pageContext['request'].userPrincipal.name}"/>">View
									profile</a></li>
							<li><a href="<c:url value="/logout"/>">Log out</a></li>

						</ul>
					</div>
				</sec:authorize>
				<sec:authorize access="!isAuthenticated()">
					<a class="btn btn-primary" href="<c:url value="/login"/>">Log
						in</a>
					<a class="btn btn-info" href="<c:url value="/user/create"/>">Create
						new user</a>
				</sec:authorize>
			</div>
		</div>
	</div>

	<c:choose>
		<c:when test="${empty navigation}">
			<a class="btn btn-danger" href="<c:url value="/"/>"><i
				class="icon-home icon-white"></i>Return to home</a>
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
	<script src="<c:url value="http://code.jquery.com/jquery-latest.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.js"/>"></script>
</body>
</html>