<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>Not Found</title>
</head>
<body>
	<p>(${pageContext.errorData.statusCode}) Resource not found:</p>
	<p style="font-weight: bold;">
		<c:out value="${pageContext.errorData.requestURI}" />
	</p>
</body>
</html>