<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<table class="table table-striped">
	<tr>
		<th>Name</th>
		<th>E-mail</th>
		<th>Roles</th>
	</tr>
	<c:forEach var="user" items="${users}">
		<tr>
			<td><a href="<c:url value="/user/${user.id}"/>"><c:out
						value="${user.name}" /></a> <c:if test="${!user.enabled}">
					<span class="text-warning">(disabled)</span>
				</c:if></td>
			<td><c:out value="${user.email}" /></td>
			<td><t:list items="${user.roles}" /></td>
		</tr>
	</c:forEach>
</table>
<c:if test="${not empty pagedListHolder}">
	<t:paging pagedLink="?page=~" pagedListHolder="${pagedListHolder}" />
</c:if>