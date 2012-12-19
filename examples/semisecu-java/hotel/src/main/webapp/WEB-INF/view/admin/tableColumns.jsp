<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:forEach var="col" items="${columns}">
	<label class="checkbox"> <input type="checkbox" name="col"
		checked="checked" value="${col}" /> <c:out value="${col}" />
	</label>
</c:forEach>