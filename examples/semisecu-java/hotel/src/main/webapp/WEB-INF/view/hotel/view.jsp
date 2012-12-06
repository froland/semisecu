<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Hotel: ${hotel.hotelName}</title>
</head>
<body>
	<div>
		<a href="<c:url value="/hotel"/>">List of hotels</a>
	</div>
	<div class="container">
		<h1>Hotel: ${hotel.hotelName}</h1>
		<div>Address: ${hotel.address}</div>
		<div>
			<h3>Comments</h3>
			<c:forEach var="comment" items="${hotel.comments}">
				<div class="comment">
					<p>User: ${comment.user.name}</p>
					${comment.text}
				</div>
			</c:forEach>
			<sec:authorize access="hasRole('user')">
				<div class="newComment">
					<form action="<c:url value="/hotel/${hotel.id}/comment"/>"
						method="POST">
						<label for="text">Text:</label>
						<div>
							<textarea rows="3" cols="60" name="text"></textarea>
						</div>
						<input type="submit" value="Add comment" />
					</form>
				</div>
			</sec:authorize>
		</div>
	</div>
</body>
</html>