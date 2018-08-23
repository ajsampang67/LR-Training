<%@ include file="/init.jsp" %>

<%--
Reason for scriptlets:
	- Keep getting "No method ___ found" or "No enum constant"
(ex. Month.January) if being called inside JSTL.
	- When using JSTL for-loop over scriptlet,
	can't access value through pageContext
	(or couldn't find a way to access at all)
	**TODO See Onenote for this fix
--%>

<liferay-ui:tabs
	names="${fn:join(years, ',')}"
	refresh="false"
	type="tabs nav-tabs-default"
>
	<c:forEach items="${years}" var="year">
		<c:set value="${issuesByYear.get(year)}" var="issuesThisYear" />

		<liferay-ui:section>
			<c:set value="${monthsByYear.get(year)}" var="months" />

			<c:forEach items="${months}" var="month">
				<div style="text-align:center;">
					<liferay-ui:header title="${month}" />

					<c:forEach items="issuesThisYear" var="issue">

						<%--
						1) Figure out how to get custom JSTL to work
							- currently get missing capability "osgi.extender"
								in gogo, module won't start
						2) Use scriptlets
							- https://stackoverflow.com/questions/19528605/how-to-access-to-iteration-variable-in-cforeach-with-a-scriptlet-expression
						<c:set
							value="${myfn:getArticleFieldValue(issue,
							'issueDate')}"
							var="issueDate"
						/>

						<c:set
							value="${myfn:getMonthDisplayName(issueDate)}"
							var="issueMonth"
						/>

						<c:if test="${issueMonth.equals(month)}">
							test
						</c:if>
						--%>
					</c:forEach>
				</div>
			</c:forEach>
		</liferay-ui:section>
	</c:forEach>
</liferay-ui:tabs>