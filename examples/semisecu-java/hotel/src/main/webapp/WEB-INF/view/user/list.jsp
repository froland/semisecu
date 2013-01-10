<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:if test="${empty userTableType}">
	<c:set var="userTableType" value="userTable.jsp" />
</c:if>
<t:page.template>
	<jsp:attribute name="title">User list</jsp:attribute>
	<jsp:attribute name="navigation">
		<t:navigation.admin />
	</jsp:attribute>
	<jsp:body>
	    <jsp:include page="${userTableType}" />
	</jsp:body>
</t:page.template>