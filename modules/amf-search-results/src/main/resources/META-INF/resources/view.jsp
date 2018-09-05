<%@ include file="init.jsp" %>

<%
	String zip = ParamUtil.getString(request, "zip");

	// Send zip between pages in results container

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("zip", zip);
%>

	<liferay-ui:search-container
		delta="5"
		emptyResultsMessage="No results found.
							Please try a different search criteria."
		iteratorURL="<%= portletURL %>"
	>

<%

		// Get all addresses first

		List allAddresses = AddressLocalServiceUtil.getAddresses();
		List userHits = new ArrayList();

		// Parse to get addresses with matching zip code, then grab user

		for (Object o : allAddresses) {
			Address a = (Address)o;

			if (a.isPrimary() && a.getZip().equals(zip)) {
				User u = UserLocalServiceUtil.getUser(a.getUserId());
				userHits.add(u);
			}
		}

		List userResults = ListUtil.subList(userHits,
												searchContainer.getStart(),
												searchContainer.getEnd());
%>

	<liferay-ui:search-container-results />

<%
		searchContainer.setResults(userResults);
		searchContainer.setTotal(userHits.size());
%>

<liferay-ui:search-container-row
	className="com.liferay.portal.kernel.model.User"
	modelVar="user"
>
			<% String resultVal =
				user.getFirstName() + " " +
				user.getLastName().substring(0,1) + ". " +
				"("+ user.getScreenName() + ") - " +
				user.getEmailAddress();
			%>

			<liferay-ui:search-container-column-text value="<%= resultVal %>" />
		</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
	</liferay-ui:search-container>