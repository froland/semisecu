<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page.template>
	<jsp:attribute name="title">Page not found</jsp:attribute>
	<jsp:body>
	<p>(${pageContext.errorData.statusCode}) Resource not found:</p>
	<p style="font-weight: bold;">
		<c:out value="${pageContext.errorData.requestURI}" />
	</p>
	</jsp:body>
</t:page.template>