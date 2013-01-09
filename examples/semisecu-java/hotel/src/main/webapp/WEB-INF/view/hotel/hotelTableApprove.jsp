<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<table class="table table-striped">
	<tr>
		<th>Hotel name</th>
		<th><!-- approve --></th>
	</tr>
	<c:forEach var="hotel" items="${hotels}">
		<tr>
			<td><a href="<c:url value="/hotel/${hotel.id}"/>"><c:out
						value="${hotel.name}" /></a></td>
			<td>
			<form method="POST"
					action="<c:url value="/hotel/${hotel.id}/approve"/>">
				<input type="submit" value="Approve hotel" class="btn btn-success" />
			</form>
			</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${not empty pagedListHolder}">
	<t:paging pagedLink="?page=~" pagedListHolder="${pagedListHolder}" />
</c:if>