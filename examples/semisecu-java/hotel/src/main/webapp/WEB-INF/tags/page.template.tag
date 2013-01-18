<%-- based on http://stackoverflow.com/questions/10529963/what-is-the-best-way-to-create-jsp-layout-template --%>
<%@tag description="Main page template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@attribute name="title" type="java.lang.String"
	description="The title of the page"%>
<%@attribute name="navigation" fragment="true"
	description="Navigation toolbar"%>
<%@attribute name="head" fragment="true"
	description="Additional elements for the head"%>
<t:page.blank title="${title}" head="${head}">
	<jsp:body>
	<div class="row-fluid">
		<div class="span4">
			<h1>OwaspHotel</h1>
		</div>
		<div class="span3 offset2">
			<div id="login">
				<sec:authorize access="isAuthenticated()">
					<div class="btn-group">
						<a class="btn btn-primary"
								href="<c:url value="/user/${pageContext['request'].userPrincipal.id}"/>"><i
								class="icon-user icon-white"></i> <sec:authentication property="name" /></a>
						<a class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
								href="#"><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a
									href="<c:url value="/user/${pageContext['request'].userPrincipal.id}"/>">View
									profile</a></li>
							<sec:authorize access="hasRole('ADMIN')">
								<li><a href="<c:url value="/user"/>">User list</a></li>
								<li><a href="<c:url value="/admin"/>">Administration page</a></li>
							</sec:authorize>
							<li><a href="<c:url value="/logout"/>">Log out</a></li>

						</ul>
					</div>
				</sec:authorize>
				<sec:authorize access="!isAuthenticated()">
					<a class="btn btn-primary" href="<c:url value="/login"/>">Log
						in</a>
					<a class="btn btn-info" href="<c:url value="/user/create"/>">Sign up</a>
				</sec:authorize>
			</div>
		</div>
		<div class="span2 offset1">
		<form action="<c:url value="/hotel/search"/>" method="get" class="form-search pull-right">
		<div class="input-append">
			<input type="text" name="t" title="Search for a hotel"
				id="hotelSearchField" class="search-query" placeholder="Search hotel" />
			<button type="submit" class="btn"><i class="icon-search"></i></button>
		</div>
<script type="text/javascript">
	$(function() {
		$('#hotelSearchField').typeahead({
			'source' : function(query, process) {
				return $.getJSON("<c:url value="/hotel/searchAutocomplete"/>", {
					't' : query
				}, process);
			}
		});
	});
</script>
		</form>
		</div>
	</div>
	<c:choose>
		<c:when test="${empty navigation}">
		<div class="navbar">
			<div class="navbar-inner">
			<ul class="nav">
			<li><a href="<c:url value="/"/>"><i class="icon-home"></i>Return to home</a></li>
			</ul>
			</div>
		</div>
		</c:when>
		<c:otherwise>
			<div id="navigation">
				<jsp:invoke fragment="navigation" />
			</div>
		</c:otherwise>
	</c:choose>
	<h1><c:out value="${title}" /></h1>
	<c:if test="${not empty SUCCESS_MESSAGE }">
	<div class="alert alert-success">
		<c:out value="${SUCCESS_MESSAGE}" />
	</div>
	</c:if>
	<div id="body">
		<jsp:doBody />
	</div>
</jsp:body>
</t:page.blank>