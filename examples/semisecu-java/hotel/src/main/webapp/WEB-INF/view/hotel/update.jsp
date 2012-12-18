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
</style>
	</jsp:attribute>
	<jsp:attribute name="navigation">
		<t:navigation.hotel />
	</jsp:attribute>
	<jsp:attribute name="title">
		<c:choose>
		<c:when test="${empty hotel.id}">
		New hotel
		</c:when>
		<c:otherwise>
		Updating hotel
		</c:otherwise>
		</c:choose>
	</jsp:attribute>
	<jsp:body>
		<form:form modelAttribute="hotel">
			<form:errors path="*" />
			<table>
				<tr>
					<td>Name</td>
					<td><form:input path="hotelName" /></td>
				</tr>
				<tr>
					<td>Address</td>
					<td><form:textarea path="address" /></td>
				</tr>
				<tr>
					<td>City</td>
					<td><form:input path="city" /></td>
				</tr>
				<tr>
					<td>Country</td>
					<td><form:input path="country" /></td>
				</tr>
				<tr>
					<td>Telephone</td>
					<td><form:input path="telephone" /></td>
				</tr>
				<tr>
					<td>E-mail</td>
					<td><form:input path="email" /></td>
				</tr>
				<tr>
					<td>Stars</td>
					<td><form:select path="stars">
					<c:forEach var="i" begin="1" end="5">
					<form:option value="${i}" />
					</c:forEach>
					</form:select></td>
				</tr>
			<tr>
					<td />
					<td>
					<c:choose>
					<c:when test="${empty hotel.id}">
					<input type="submit" value="Add hotel" />
					</c:when>
					<c:otherwise>
					<input type="submit" value="Update hotel" />
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</table>
		</form:form>
		<c:if test="${not empty hotel.id}">
		<form method="POST" enctype="multipart/form-data"
				action="<c:url value="/hotel/${hotel.id}/image"/>">
		<img src="<c:url value="/hotel/${hotel.id}/image"/>" alt="Hotel image"
					class="hotelImage" />
		Image: <input type="file" name="file" />
		<input type="submit" value="Upload" />
		</form>
		</c:if>
	</jsp:body>
</t:page.template>