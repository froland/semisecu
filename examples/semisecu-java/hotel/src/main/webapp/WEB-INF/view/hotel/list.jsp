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
<c:if test="${empty hotelTableType}">
	<c:set var="hotelTableType" value="hotelTable.jsp" />
</c:if>
<t:page.template>
	<jsp:attribute name="navigation">
		<t:navigation.hotel />
	</jsp:attribute>
	<jsp:attribute name="title">${pageTitle}</jsp:attribute>
	<jsp:body>
	    <jsp:include page="${hotelTableType}" />
	</jsp:body>
</t:page.template>