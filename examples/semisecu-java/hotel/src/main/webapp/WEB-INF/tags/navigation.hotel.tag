<%@tag description="Navigation bar for hotels" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="navbar">
	<div class="navbar-inner">
		<ul class="nav">
			<li><a href="<c:url value="/hotel"/>">List of hotels</a></li>
			<li><a href="<c:url value="/hotel/top"/>">Top hotels</a></li>
			<sec:authorize access="hasRole('ADMIN')">
				<li><a href="<c:url value="/hotel/toApprove"/>">Hotels to approve</a></li>
			</sec:authorize>
			<sec:authorize access="hasRole('USER')">
				<li><a href="<c:url value="/hotel/managed"/>">My hotels</a></li>
				<li><a href="<c:url value="/hotel/create"/>">Create a new hotel</a></li>
			</sec:authorize>
		</ul>
	</div>
</div>
