<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<t:page.template>
	<jsp:attribute name="navigation">
		<t:navigation.admin />
	</jsp:attribute>
	<jsp:attribute name="title">Administration</jsp:attribute>
	<jsp:body>
		<div>
			<h2>Export a table</h2>
			<form action="<c:url value='/admin/export' />" method="get">
				<label for="tableName">Table name</label>
				<input type="text" name="tableName" id="dumpTable">
				<div id="dumpTableColumns"></div>
				<input class="btn btn-primary" type="submit" value="Dump" />
<script type="text/javascript">
	$(function() {
		var divCols = $("#dumpTableColumns");
		$('#dumpTable').typeahead({
			'source' : ${tables},
			'updater': function(item) {
				// update the list of columns
				$.get("<c:url value="/admin/tableColumns"/>", {
					'tableName': item
				}, function(data) {
					divCols.html(data);
				});
				return item;
			}
		});
	});
</script>
			</form>
		</div>
	</jsp:body>
</t:page.template>