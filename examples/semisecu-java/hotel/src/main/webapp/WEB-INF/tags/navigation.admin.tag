<%@tag description="Navigation bar for administration"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="navbar">
	<div class="navbar-inner">
		<ul class="nav">
			<li><a href="<c:url value="/"/>"><i class="icon-home"></i>Return
					to home</a></li>
			<li><a href="<c:url value="/hotel/toApprove"/>">Hotels to
					approve</a></li>
			<li><a href="<c:url value="/user" />">User list</a></li>
			<li><a href="<c:url value="/admin" />">Administration page</a></li>
		</ul>
	</div>
</div>
