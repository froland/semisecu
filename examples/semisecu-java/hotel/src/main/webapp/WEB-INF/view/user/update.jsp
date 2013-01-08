<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="navigation">
		<t:navigation.default />
	</jsp:attribute>
	<jsp:attribute name="title">
		<c:choose>
		<c:when test="${empty user.id}">
		New user
		</c:when>
		<c:otherwise>
		Updating user
		</c:otherwise>
		</c:choose>
	</jsp:attribute>
	<jsp:body>
		<form:form modelAttribute="user" cssClass="form-horizontal">
			<form:errors element="div" path="*" cssClass="alert alert-error"></form:errors>
			<fieldset>
			 			 	 
 				<div class="control-group">
					<label class="control-label" for="userName">Name</label>
					<div class="controls">
						<form:input path="name" id="userName" value="${user.name}" />
					</div>
				</div>
				
				<div class="control-group">
					<sec:authorize access="!hasRole('ADMIN') and ${not empty user.id}">
					<label class="control-label" for="oldPassword">Old password</label>
					<div class="controls">
						<form:password path="oldPassword" id="oldPassword" />
					</div>
					</sec:authorize>
					<label class="control-label" for="userPassword">Password</label>
					<div class="controls">
						<form:password path="password" id="userPassword" />
					</div>
					<label class="control-label" for="userRetypedPassword">Re-type your Password</label>
					<div class="controls">
						<form:password path="retypedPassword" id="userPassword" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="userEmail">Email</label>
					<div class="controls">
						<form:input path="email" id="userEmail" value="${user.email}" />
					</div>
				</div>
				
				<div class="control-group">
					<div class="controls">
						<c:choose>
					<c:when test="${empty user.id}">
					<button type="submit" class="btn btn-primary"> Add user </button>
					</c:when>
					<c:otherwise>
					<button type="submit" class="btn btn-primary"> Update user </button>
					</c:otherwise>
					</c:choose>
					</div>
				</div>
			</fieldset>
		</form:form>
	</jsp:body>
</t:page.template>