<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="head">
<style type="text/css">
img.hotelImage {
	width: 200px;
}

.comment {
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
</style>
	</jsp:attribute>
	<jsp:attribute name="navigation">
		<t:navigation.hotel />
	</jsp:attribute>
	<jsp:attribute name="title">Hotel: ${hotel.hotelName}</jsp:attribute>
	<jsp:body>
<<<<<<< HEAD
		<div class="row">
		<div class="span4 offset1">
		<img src="<c:url value="/hotel/${hotel.id}/image"/>" alt="Hotel image"
			class="hotelImage" />
		</div>
		<c:if test="${!hotel.approved}">
			<p class="alert">The hotel is not approved!</p>
			<sec:authorize access="hasRole('admin')">
			<form method="POST"
					action="<c:url value="/hotel/${hotel.id}/approve"/>">
				<input type="submit" value="Approve hotel" class="btn btn-success" />
			</form>
			</sec:authorize>
			</div>
		</c:if>
		<div class="span5 offset1">
		<img src="<c:url value="/hotel/${hotel.id}/image"/>" alt="Hotel image"
			class="hotelImage" />
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
			<p>Manager: <a
					href="<c:url value="/user/${hotel.manager.name}"/>">${hotel.manager.name}</a>
			</p>
			<div class="description">
			${hotel.descriptionHTML}
			</div>
			<c:if
				test="${hotel.manager.name == pageContext['request'].userPrincipal.name}">
			<p>
				<a class="btn btn-warning"
						href="<c:url value="/hotel/${hotel.id}/update" />"><i
						class="icon-edit icon-white"></i> Update the hotel</a>
			</p>
			</c:if>
		</div>
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
					<button type="submit" class="btn btn-danger"> <i
											class="icon-trash icon-white"></i> Delete</button>
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
					<button class="btn btn-success" type="submit"> <i
							class="icon-comment icon-white"></i> Add comment</button>
				</form>
			</div>
		</div>
	</jsp:body>
</t:page.template>