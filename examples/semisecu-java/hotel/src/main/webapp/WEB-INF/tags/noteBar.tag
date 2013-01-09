<%@tag description="Shows a bar" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="noted" required="true" type="com.hermes.owasphotel.domain.Noted"%>
<div id="progress-bar" style="position:relative;">
	<div style="position:absolute; z-index: 2; vertical-align: middle; text-align: center; width: 100%; height: 39px; background: rgb(255, 255, 255) transparent;">
		<div style="display: table; width: 100%; height:39px">
			<p style="display: table-cell; vertical-align: middle; text-align: center; ">${noted.printableNote}</p>
		</div>
	</div>
		<div id="progress-level" style="display: table-cell; vertical-align: middle; position:absolute; z-index: 1; width: ${noted.noteBarLength}%"> </div>
</div>