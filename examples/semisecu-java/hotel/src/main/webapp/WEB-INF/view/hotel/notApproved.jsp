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
	<jsp:attribute name="title">Hotel: ${hotel.hotelName}</jsp:attribute>
	<jsp:body>
		<p>The hotel you chose has not been yet approved.</p>
		<sec:authorize access="hasRole('admin')">
		<form method="POST"
				action="<c:url value="/hotel/${hotel.id}/approve"/>">
			<input type="submit" value="Approve hotel" />
		</form>
		</sec:authorize>
	</jsp:body>
</t:page.template>