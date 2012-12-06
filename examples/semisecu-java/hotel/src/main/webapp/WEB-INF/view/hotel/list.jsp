<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Hotels</title>
</head>
<body>
	<div class="container">
		<h1>Hotels</h1>
		<div>
			<table>
				<tr>
					<th>Hotel name</th>
					<th>Address</th>
				</tr>
				<c:forEach var="hotel" items="${hotels}">
					<tr>
						<td><a href="<c:url value="hotel/${hotel.id}"/>"><c:out
									value="${hotel.hotelName}" /></a></td>
						<td><c:out value="${hotel.address}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>