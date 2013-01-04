<%-- based on http://stackoverflow.com/questions/10529963/what-is-the-best-way-to-create-jsp-layout-template --%>
<%@tag description="Blank page template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title" type="java.lang.String"
	description="The title of the page"%>
<%@attribute name="head" fragment="true"
	description="Additional elements for the head"%>
<!DOCTYPE html>
<html>
<head>
<title><c:out value="${title}" /></title>
<link href="<c:url value="/css/bootstrap.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/hotel.css"/>" type="text/css"
	rel="stylesheet" />
<script src="<c:url value="/js/jquery.js"/>"></script>
<%-- jQuery can be found at: http://code.jquery.com/ --%>
<script src="<c:url value="/js/bootstrap.js"/>"></script>
<jsp:invoke fragment="head" />
</head>
<body>
	<jsp:doBody />
</body>
</html>
