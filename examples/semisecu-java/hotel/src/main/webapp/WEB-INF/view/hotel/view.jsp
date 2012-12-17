<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="navigation">
		<t:navigation.hotel />
	</jsp:attribute>
	<jsp:attribute name="title">Hotel: ${hotel.hotelName}</jsp:attribute>
	<jsp:body>
		<c:if test="!${hotel.approved}">
			<p>The hotel is not approved!</p>
			<sec:authorize access="hasRole('admin')">
			<form method="POST"
					action="<c:url value="/hotel/${hotel.id}/approve"/>">
				<input type="submit" value="Approve hotel" />
			</form>
			</sec:authorize>
		</c:if>
		<div>
			<p>Stars: <t:hotel.stars value="${hotel.stars}" />
			</p>
			<p>Average note:
				<t:hotel.stars value="${hotel.averageNote}" max="10" />
			</p>
			<p>Address: <br />${hotel.address}<br />${hotel.city} ${hotel.country}</p>
			<p>Telephone: ${hotel.telephone}</p>
			<p>Email: <a href="mailto:${hotel.email}">${hotel.email}</a>
			</p>
			<c:if
				test="${hotel.createdBy.name == pageContext['request'].userPrincipal.name}">
			<p>
				<a href="<c:url value="/hotel/${hotel.id}/update" />">Update the hotel</a>
			</p>
			</c:if>
		</div>
		<div>
			<h3>Comments</h3>
			<c:forEach var="comment" items="${hotel.comments}">
			<c:if test="${!comment.deleted}">
				<div class="comment">
					<div>${comment.userName} commented on ${comment.date}
					Note: ${comment.note}
					<sec:authorize access="hasRole('admin')">
					<form method="POST"
									action="<c:url value="/hotel/${hotel.id}/comment"/>">
					<input type="hidden" name="delete" value="${comment.sequence}" />
					<input type="submit" value="Delete">
					</form>
					</sec:authorize>
					</div>
					${comment.text}
				</div>
			</c:if>
			</c:forEach>
			
			<!-- Add a new comment -->
			<div class="newComment">
				<form action="<c:url value="/hotel/${hotel.id}/comment"/>"
					method="POST">
					<sec:authorize access="!isAuthenticated()">
					<label for="name">Name:</label>
					<input name="name" />
					</sec:authorize>
					<label for="note">Note:</label>
					<select name="note">
					<c:forEach var="i" begin="1" end="10">
					<option>${i}</option>
					</c:forEach>
					</select>
					<br />
					<label for="text">Text:</label>
					<div>
						<textarea rows="3" cols="60" name="text"></textarea>
					</div>
					<input type="submit" value="Add comment" />
				</form>
			</div>
		</div>
	</jsp:body>
</t:page.template>