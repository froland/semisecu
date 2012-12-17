<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:if test="${empty pageTitle}">
	<c:set var="pageTitle" value="Hotels" />
</c:if>
<t:page.template>
	<jsp:attribute name="navigation">
		<t:navigation.hotel />
	</jsp:attribute>
	<jsp:attribute name="title">${pageTitle}</jsp:attribute>
	<jsp:body>
	<table class="table table-striped">
		<tr>
			<th>Hotel name</th>
			<th>City</th>
		</tr>
		<c:forEach var="hotel" items="${hotels}">
			<tr>
				<td><a href="<c:url value="/hotel/${hotel.id}"/>"><c:out
								value="${hotel.hotelName}" /></a></td>
				<td><c:out value="${hotel.city}" /></td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${not empty pagedListHolder}">
	<t:paging pagedLink="?page=~" pagedListHolder="${pagedListHolder}" />
	</c:if>
	</jsp:body>
</t:page.template>