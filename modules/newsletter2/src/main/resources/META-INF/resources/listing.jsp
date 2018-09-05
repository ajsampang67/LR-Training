<%@ include file="/init.jsp" %>

<c:set value="${issuesByYearAndMonth}" var="issuesByYearAndMonth" />

<liferay-ui:tabs
	names="${fn:join(years, ',')}"
	refresh="false"
	type="tabs nav-tabs-default"
>
	<c:forEach items="${years}" var="year">
		<liferay-ui:section>
			<c:set value="${monthsByYear.get(year)}" var="months" />

			<c:forEach items="${trueMonths}" var="month">
				<c:if test="${months.contains(month)}">
					<div style="text-align:center;">
						<liferay-ui:header title="${month}" />
					</div>

					<%-- Had to use scriptlets, can't use Utils in JSTL --%>

					<%
					long groupId = themeDisplay.getScopeGroupId();
					String year = (String)pageContext.getAttribute("year");
					String month = (String)pageContext.getAttribute("month");
					HashMap<String, List<JournalArticle>> issuesByYearAndMonth =
							(HashMap<String, List<JournalArticle>>)
									pageContext.getAttribute("issuesByYearAndMonth");
					String issuesKey = year+month;
					String languageId = LanguageUtil.getLanguageId(Locale.US);

					// Layout page of Issue view

					Layout issueLayout =
							LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, false, "/newsletter");

					long issueLayoutPlid = issueLayout.getPlid();
					LayoutTypePortlet issueLayoutTypePortlet =
							(LayoutTypePortlet)issueLayout.getLayoutType();

					// Getting Asset Publisher portlet from layout

					List<Portlet> allPortlets =
							issueLayoutTypePortlet.getAllPortlets();
					String assetPublisherPortletName = StringPool.BLANK;

					for (Portlet portlet : allPortlets) {
						if (portlet.getRootPortletId().equals(Newsletter2PortletKeys.ASSET_PUBLISHER_ID)) {
							assetPublisherPortletName =
									portlet.getPortletId();
						}
					}

					for (JournalArticle issue : issuesByYearAndMonth.get(issuesKey)) {
						%>

                        <liferay-portlet:renderURL
                            plid="<%= issueLayoutPlid %>"
                            portletName="<%= assetPublisherPortletName %>"
                            var="issueURL"
                        >
							<liferay-portlet:param
								name="articleId"
								value="<%= issue.getArticleId() %>"
							/>
						</liferay-portlet:renderURL>

						<a href="<%= issueURL %>">
						<%= JournalArticleLocalServiceUtil.getArticleContent(
								issue,
								Newsletter2PortletKeys.ISSUE_LISTING_TEMPLATE_KEY,
								ActionKeys.VIEW,
								languageId,
								new PortletRequestModel(
									renderRequest, renderResponse), themeDisplay) %>
						</a>

						<hr style="border-top:dashed 1px;color:gray;" />

					<%
					}
					%>

				</c:if>
			</c:forEach>
		</liferay-ui:section>
	</c:forEach>
</liferay-ui:tabs>