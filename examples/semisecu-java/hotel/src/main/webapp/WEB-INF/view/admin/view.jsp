<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="title">Administration</jsp:attribute>
	<jsp:body>
		<div>
			<h2>List the users</h2>
			<a href="<c:url value="/user" />">User list</a>
		</div>
		<div>
			<h2>Export a table</h2>
			<form name='dumpForm' action="<c:url value='/admin/export' />"
				method='GET'>
				<table>
				<tr>
					<td><label for="tableName">Table name</label></td>
					<td><input type='text' name='tableName' value=''></td>
				</tr>
				<tr>
					<td />
					<td><input class="btn btn-primary" type="submit" value="Dump" /></td>
				</tr>
				</table>
			</form>
		</div>
	</jsp:body>
</t:page.template>