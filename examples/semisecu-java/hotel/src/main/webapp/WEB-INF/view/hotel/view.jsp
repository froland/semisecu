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
			<t:hotel.stars stars="${hotel.stars}" />
			<p>Address: <br />${hotel.address}<br />${hotel.city} ${hotel.country}</p>
			<p>Telephone: ${hotel.telephone}</p>
			<p>Email: <a href="mailto:${hotel.email}">${hotel.email}</a>
			</p>
			<sec:authorize access="hasRole('admin')">
			<p>
				<a href="<c:url value="/hotel/${hotel.id}/update" />">Update the hotel</a>
			</p>
			</sec:authorize>
		</div>
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
	</jsp:body>
</t:page.template>