<%@tag description="Wrapper for a simple select HTML tag"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="begin" required="false" type="java.lang.Integer"%>
<%@attribute name="end" required="true" type="java.lang.Integer"%>
<%@attribute name="value" required="false" type="java.lang.Integer"%>
<c:if test="${empty begin}">
	<c:set var="begin" value="1" />
</c:if>
<c:forEach var="i" begin="${begin}" end="${end}">
	<c:choose>
		<c:when test="${i == value}">
			<option selected="selected">${i}</option>
		</c:when>
		<c:otherwise>
			<option>${i}</option>
		</c:otherwise>
	</c:choose>
</c:forEach>