<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page.blank>
	<jsp:attribute name="title">Attack</jsp:attribute>
	<jsp:attribute name="head"></jsp:attribute>
	
<jsp:body>
	<h2>Attack Successfull</h2>
	<div>
	<c:out value="${param.data}"></c:out> 
	</div>
</jsp:body>
</t:page.blank>
	