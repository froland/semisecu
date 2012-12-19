<%@tag description="List of string elements" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="items" required="true" type="java.lang.Iterable"%>
<%@attribute name="separator" required="false" type="java.lang.String"%>
<c:if test="${empty separator}">
	<c:set var="separator" value=", " />
</c:if>
<c:forEach var="i" items="${items}" varStatus="it"><c:if test="${!it.first}">${separator}</c:if><c:out value="${i}" /></c:forEach>