<%@ tag import="org.springframework.util.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="pagedListHolder" required="true"
	type="org.springframework.beans.support.PagedListHolder"%>
<%@ attribute name="pagedLink" required="true" type="java.lang.String"%>
<%--From: https://nhin2010.jira.com/svn/VDP/tags/AUTO_201237/WebMail/vdp/src/main/webapp/WEB-INF/tags/paging.tag --%>

<c:if test="${pagedListHolder.pageCount > 1}">
	<div class="pagination">
		<ul>
			<c:if test="${!pagedListHolder.firstPage}">
				<li><a
					href="<%=StringUtils.replace(pagedLink, "~",
							String.valueOf(pagedListHolder.getPage() - 1))%>">Prev</a></li>
			</c:if>
			<c:if test="${pagedListHolder.firstLinkedPage > 0}">
				<li><a href="<%=StringUtils.replace(pagedLink, "~", "0")%>">1</a></li>
			</c:if>
			<c:if test="${pagedListHolder.firstLinkedPage > 1}">
				<li class="disabled"><a href="#">...</a></li>
			</c:if>
			<c:forEach begin="${pagedListHolder.firstLinkedPage}"
				end="${pagedListHolder.lastLinkedPage}" var="i">
				<c:choose>
					<c:when test="${pagedListHolder.page == i}">
						<li class="active"><a href="#">${i+1}</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="<%=StringUtils
									.replace(pagedLink, "~", String
											.valueOf(jspContext
													.getAttribute("i")))%>">${i+1}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if
				test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 2}">
				<li class="disabled"><a href="#">...</a></li>
			</c:if>
			<c:if
				test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 1}">
				<li><a
					href="<%=StringUtils.replace(pagedLink, "~",
							String.valueOf(pagedListHolder.getPageCount() - 1))%>">${pagedListHolder.pageCount}</a></li>
			</c:if>
			<c:if test="${!pagedListHolder.lastPage}">
				<li><a
					href="<%=StringUtils.replace(pagedLink, "~",
							String.valueOf(pagedListHolder.getPage() + 1))%>">Next</a></li>
			</c:if>
		</ul>
	</div>
</c:if>