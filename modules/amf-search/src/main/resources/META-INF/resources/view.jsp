<%@ include file="/init.jsp" %>

<form action="<portlet:actionURL/>" class="aui top-label" method="post">
	<aui:fieldset class="top-label">
		<aui:input
			class="text"
			id="zip"
			label=""
			name="zip"
			type="text"
		/>

		<div style="text-align: right">
			<aui:button label="Submit" type="search" value="Search" />
		</div>
	</aui:fieldset>
</form>