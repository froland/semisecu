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
			<sec:authorize access="hasRole('admin')">		
			<form name='dumpForm' action="<c:url value='/admin/export' />"
					method='GET'>
				<table>
				<tr>
					<td>Table name:</td>
					<td><input type='text' name='tableName' value=''></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit" value="Dump" /></td>
				</tr>
				</table>
			</form>		
			</sec:authorize>
		</div>	
	</jsp:body>
</t:page.template>