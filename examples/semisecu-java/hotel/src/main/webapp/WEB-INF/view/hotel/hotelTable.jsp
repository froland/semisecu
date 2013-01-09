<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<table class="table table-striped">
	<tr>
		<th>Hotel name</th>
		<th>Note</th>
		<th>Comments</th>
	</tr>
	<c:forEach var="hotel" items="${hotels}">
		<tr>
			<td><img src="<c:url value="/hotel/${hotel.id}/image"/>" alt=""
				class="hotelImageTiny" /><a
				href="<c:url value="/hotel/${hotel.id}"/>"><c:out
						value="${hotel.name}" /></a></td>
			<td><fmt:formatNumber type="number" maxFractionDigits="2"
					value="${hotel.note }" /></td>
			<td><c:out value="${hotel.nbComments}" /></td>
		</tr>
	</c:forEach>
</table>
<c:if test="${not empty pagedListHolder}">
	<t:paging pagedLink="?page=~" pagedListHolder="${pagedListHolder}" />
</c:if>