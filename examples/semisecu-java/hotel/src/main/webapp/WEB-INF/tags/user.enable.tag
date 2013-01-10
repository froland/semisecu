<%@ tag description="Form to enable/disable the user"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="user" required="true"
	type="com.hermes.owasphotel.domain.User"%>
<sec:authorize access="hasRole('ADMIN')">
	<form action="<c:url value="/user/enable/${user.id}"/>"
		method="post" class="enableUser">
		<c:choose>
			<c:when test="${user.enabled}">
				<input type="hidden" name="enable" value="false" />
				<input type="submit" class="btn-small btn-warning"
					value="Disable account" />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="enable" value="true" />
				<input type="submit" class="btn-small btn-warning"
					value="Enable account" />
			</c:otherwise>
		</c:choose>
	</form>
</sec:authorize>