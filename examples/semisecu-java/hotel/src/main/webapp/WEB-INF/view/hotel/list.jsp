<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:page.template>
	<jsp:attribute name="navigation">
		<t:navigation.hotel />
	</jsp:attribute>
	<jsp:attribute name="title">Hotels</jsp:attribute>
	<jsp:body>
	<table>
		<tr>
			<th>Hotel name</th>
			<th>Address</th>
		</tr>
		<c:forEach var="hotel" items="${hotels}">
			<tr>
				<td><a href="<c:url value="hotel/${hotel.id}"/>"><c:out
								value="${hotel.hotelName}" /></a></td>
				<td><c:out value="${hotel.address}" /></td>
			</tr>
		</c:forEach>
	</table>
	</jsp:body>
</t:page.template>