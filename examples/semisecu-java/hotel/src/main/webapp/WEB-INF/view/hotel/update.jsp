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
	<div>
		<form:form modelAttribute="hotel" cssClass="form-horizontal" enctype="multipart/form-data" >
			<form:errors path="*" />
			<c:if test="${not empty hotel.id}">
			<img src="<c:url value="/hotel/${hotel.id}/image"/>" alt="Hotel image"
						class="hotelImage" />
			</c:if>
			 <fieldset>
			 	<div class="control-group">
					<label class="control-label" for="hotelImage">Image</label>
					<div class="controls">
						<input type="file" name="file" id="hotelImage"/>
					</div>
				</div>
			 			 	 
 				<div class="control-group">
					<label class="control-label" for="hotelName">Name</label>
					<div class="controls">
						<form:input path="hotelName" id="hotelName" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelAddress">Address</label>
					<div class="controls">
						<form:textarea path="address" id="hotelAddress" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelCity">City</label>
					<div class="controls">
						<form:input path="city" id="hotelCity" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelCountry">Country</label>
					<div class="controls">
						<form:input path="country" id="hotelCountry" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelTelephone">Telephone</label>
					<div class="controls">
						<form:input path="telephone" id="hotelTelephone" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelEmail">Email</label>
					<div class="controls">
						<form:input path="email" id="hotelEmail" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelStars">Stars</label>
					<div class="controls">
						<form:select path="stars" id="hotelStars">
							<c:forEach var="i" begin="1" end="5">
							<form:option value="${i}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="descriptionHTML">Description</label>
					<div class="controls">
						<form:textarea path="descriptionHTML" />
					</div>
				</div>
				
				
				<div class="control-group">
				<div class="controls">
				<c:choose>
					<c:when test="${empty hotel.id}">
					<input type="submit" value="Add hotel" />
					</c:when>
					<c:otherwise>
					<input type="submit" value="Update hotel" />
					</c:otherwise>
					</c:choose>
				</div>
				</div>
				
			</fieldset>
		
			</form:form>
		
		</div>
	</jsp:body>
</t:page.template>