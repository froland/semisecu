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
	<div class="span10 offset1">
		<form:form modelAttribute="hotel" cssClass="form-horizontal"
				enctype="multipart/form-data">
				
				<form:errors element="div" path="*" cssClass="alert alert-error"></form:errors>
			<div class="row">
		<div class="span4">
		<div class="row">
			<c:choose>
			<c:when test="${empty hotel.id}">
			<img src="<c:url value="/hotel/image/default"/>" alt="Hotel image"
										class="hotelImage" />
			</c:when>
			<c:otherwise>
			<img src="<c:url value="/hotel/${hotel.id}/image"/>"
										alt="Hotel image" class="hotelImage" />
			</c:otherwise>
			</c:choose>
		</div>
		<div class="row">
			<input type="file" name="file" id="hotelImage" />
			<br />
			<label class="checkbox">
			<input type="checkbox" name="deleteFile" />
			Delete the file</label>
		</div>
		</div>
				<div class="span5 offset1">
				<fieldset>
			 			 	 
 				<div class="control-group">
					<label class="control-label" for="hotelName">Name</label>
					<div class="controls">
						<form:input path="name" id="hotelName" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelStreet">Street</label>
					<div class="controls">
						<form:textarea path="street" id="hotelStreet" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelNumber">Number</label>
					<div class="controls">
						<form:input path="number" id="hotelNumber" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelCity">City</label>
					<div class="controls">
						<form:input path="city" id="hotelCity" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="hotelZIP">ZIP code</label>
					<div class="controls">
						<form:input path="ZIPCode" id="hotelZIP" />
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
						<form:option value="${i}" />
						</c:forEach>
						</form:select>
					</div>
				</div>
				
				<sec:authorize access="hasRole('ADMIN')">
				<div class="control-group">
					<label class="control-label" for="hotelManager">Manager</label>
					<div class="controls">
						<form:input path="manager" id="hotelManager" />
					</div>
<script type="text/javascript">
	$(function() {
		$('#hotelManager').typeahead({
			'source' : function(query, process) {
				return $.getJSON("<c:url value="/user/listNames"/>", {
					'prefix' : query
				}, process);
			}
		});
	});
</script>
				</div>
				</sec:authorize>
				
				</fieldset>
				</div>
				<div class="row">
				
				
					<label>Description (HTML)</label>
					
					<form:textarea path="descriptionHTML" cssClass="span11" rows="13" />
				
				
				
				<div class="control-group">
				<div class="controls">
				<c:choose>
					<c:when test="${empty hotel.id}">
					<button type="submit" class="btn btn-success">
											<i class="icon-plus-sign icon-white"></i> Add hotel</button>
					</c:when>
					<c:otherwise>
					<button type="submit" class="btn btn-success">
											<i class="icon-refresh icon-white"></i> Update hotel</button>
					</c:otherwise>
					</c:choose>
				</div>
				</div>
				
			</div>
		</div>
			</form:form>
		
		</div>
	</jsp:body>
</t:page.template>