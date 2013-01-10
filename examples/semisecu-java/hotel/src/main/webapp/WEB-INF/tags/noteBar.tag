<%@tag description="Shows a bar" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@attribute name="noted" required="true" type="com.hermes.owasphotel.domain.Noted"%>
<div class="progress-bar">
	<div class="progress-text-holder">
		<div class="progress-text-table">
			<p class="progress-text-paragraph"><fmt:formatNumber type="number" maxFractionDigits="0"
					value="${noted.noteValue}" /> / <fmt:formatNumber type="number" maxFractionDigits="0"
					value="${noted.maxNote}" /></p>
		</div>
	</div>
		<div class="progress-level" style="width: <fmt:formatNumber type="number" maxFractionDigits="0"
					value="${100 * noted.noteValue / noted.maxNote}" />%"> </div>
</div>