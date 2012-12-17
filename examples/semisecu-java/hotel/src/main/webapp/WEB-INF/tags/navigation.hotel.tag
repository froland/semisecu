<%@tag description="Navigation bar for hotels" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<a href="<c:url value="/hotel"/>">List of hotels</a>
<a href="<c:url value="/hotel/top"/>">Top hotels</a>
<sec:authorize access="hasRole('admin')">
	<a href="<c:url value="/hotel/all"/>">All hotels</a>
</sec:authorize>
<sec:authorize access="hasRole('user')">
	<a href="<c:url value="/hotel/create"/>">Create a new hotel</a>
</sec:authorize>