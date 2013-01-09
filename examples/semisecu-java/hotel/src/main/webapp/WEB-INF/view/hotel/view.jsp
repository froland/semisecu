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
	<jsp:attribute name="title">Hotel: ${hotel.name}</jsp:attribute>
	<jsp:body>
		<c:if test="${!hotel.approved}">
			<p class="alert">The hotel is not approved!</p>
			<sec:authorize access="hasRole('ADMIN')">
			<form method="POST"
					action="<c:url value="/hotel/${hotel.id}/approve"/>">
				<input type="submit" value="Approve hotel" class="btn btn-success" />
			</form>
			</sec:authorize>
		</c:if>
		<div class="row">
		<div class="span4 offset1">
		<img src="<c:url value="/hotel/${hotel.id}/image"/>" alt="Hotel image"
					class="hotelImage" />
		</div>
		<div class="span4">
				
			<p>Stars: <t:hotel.stars value="${hotel.stars}" />
			</p>
			<p>Average note:
				<t:noteBar noted="${hotel}" />	
			</p>
			<p>Address: <br />${hotel.completeAddress.street} ${hotel.completeAddress.number}<br />${hotel.completeAddress.ZIPCode} ${hotel.city} ${hotel.country}</p>
			<p>Telephone: ${hotel.telephone}</p>
			<p>Email: <a href="mailto:${hotel.email}">${hotel.email}</a>
			</p>
			<p>Manager: <a
						href="<c:url value="/user/${hotel.manager.name}"/>">${hotel.manager.name}</a>
			</p>
		</div>
		</div>
		<div class="row">
			<div class="span10 offset1">
			${hotel.descriptionHTML}
			</div>
		</div>
			<sec:authorize
			access="hasRole('ADMIN') or (isAuthenticated() and ${hotel.manager.name == pageContext['request'].userPrincipal.name })">
			<div class="row">
			<div class="span10 offset1">
				<a class="btn btn-warning"
						href="<c:url value="/hotel/${hotel.id}/update" />"><i
						class="icon-edit icon-white"></i> Update the hotel</a>
			</div>
			</div>
			</sec:authorize>
		<div>
			<h3>Comments</h3>
			<c:forEach var="comment" items="${hotel.comments}">
			<c:if test="${!comment.deleted}">
				<div class="comment">
					<div>
					<c:out value="${comment.userName}" /> commented on
					<fmt:formatDate value="${comment.date}" type="both" />
					<span style="float: right;">Note: <t:noteBar noted="${comment}" /></span>
					<sec:authorize access="hasRole('ADMIN')">
					<form method="POST"
									action="<c:url value="/hotel/${hotel.id}/comment"/>">
					<input type="hidden" name="delete" value="${comment.id}" />
					<button type="submit" class="btn btn-danger"> <i
											class="icon-trash icon-white"></i> Delete</button>
					</form>
					</sec:authorize>
					</div>
					<c:out value="${comment.text}" />
				</div>
			</c:if>
			</c:forEach>
			
			<!-- Add a new comment -->
			<div class="newComment">
				<form action="<c:url value="/hotel/${hotel.id}/comment"/>"
					method="POST">
					<h4>New comment</h4>
					<label for="commentNote">Note:</label>
					<select name="note" id="commentNote">
					<c:forEach var="i" begin="1" end="10">
					<option value="${i}">${i}</option>
					</c:forEach>
					</select>
					<br />
					<label for="commentText">Text:</label>
					<div>
						<textarea rows="3" class="span10" name="text" id="commentText"></textarea>
					</div>
					<button class="btn btn-success" type="submit"> <i
							class="icon-comment icon-white"></i> Add comment</button>
				</form>
			</div>
		</div>
	</jsp:body>
</t:page.template>