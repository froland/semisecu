<%@tag description="Shows the stars" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="value" required="true" type="java.lang.Integer"%>
<%@attribute name="max" required="false" type="java.lang.Integer"%>
${value}<c:if test="${not empty max}">/${max}</c:if>
