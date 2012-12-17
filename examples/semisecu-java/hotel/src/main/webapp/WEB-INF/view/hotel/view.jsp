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
				<sec:authorize access="hasRole('user')">
				<form method="POST"
						action="<c:url value="/hotel/${hotel.id}/note"/>">
				<select name="note" onchange="this.form.submit()">
				<c:forEach var="i" begin="1" end="10">
				<c:choose>
				<c:when test="${i == user_note.note}">
				<option selected="selected">${i}</option>
				</c:when>
				<c:otherwise>
				<option>${i}</option>
				</c:otherwise>
				</c:choose>
				</c:forEach>
				</select>
				</form>
				</sec:authorize>
			</p>
			<p>Address: <br />${hotel.address}<br />${hotel.city} ${hotel.country}</p>
			<p>Telephone: ${hotel.telephone}</p>
			<p>Email: <a href="mailto:${hotel.email}">${hotel.email}</a>
			</p>
			<c:if
				test="${hotel.createdBy != null and hotel.createdBy.name == pageContext['request'].userPrincipal.name}">
			<p>
				<a href="<c:url value="/hotel/${hotel.id}/update" />">Update the hotel</a>
			</p>
			</c:if>
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