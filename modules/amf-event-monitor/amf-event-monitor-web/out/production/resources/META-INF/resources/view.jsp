<%@ include file="/init.jsp" %>

<%
String tab = ParamUtil.getString(request, "tab", "All");
request.setAttribute("tab", tab);
%>

<liferay-ui:tabs
	names="All, Registration, Login"
	refresh="<%= false %>"
	tabsValues="All, Registration, Login"
	type="tabs nav-tabs-default"
	value="${tab}"
>

	<!-- All section, print everything up to 20 items using Java tags -->

	<%
	PortletURL portletURL = renderResponse.createRenderURL();
	int eventCount = EventLocalServiceUtil.getEventsCount();

	// Checking if user has permission to see all activity

	List preEventList = EventLocalServiceUtil.getEvents(0, eventCount);

	/** 1)Grab portlet, check for portlet-resource permission
	 *   VIEW-ALL-EVENTS
	 *  2) If user does not have permission to view all events, parse
	 *      list to grab only the events user created themselves
	 *  3) Else user has permission to view all events, just show all
	 */
	List eventList = new ArrayList();

	if (!permissionChecker.hasPermission(scopeGroupId, portletDisplay.getRootPortletId(), portletDisplay.getResourcePK(), "VIEW_ALL_EVENTS")) {
		System.out.println("RESOURCE PK: "+portletDisplay.getResourcePK());

		for (Object obj : preEventList) {
			Event event = (Event)obj;

			if (event.getUserId() == user.getUserId())
				eventList.add(obj);
		}
	} else {
		eventList = preEventList;
	}
	%>

	<liferay-ui:section>
		<% request.setAttribute("tab", "All"); %>
		<liferay-ui:search-container
			delta="20"
			deltaConfigurable="false"
			emptyResultsMessage="No more items"
			iteratorURL="<%= portletURL %>"
		>

			<%-- Parse list for pages --%>

			<%
			List eventResults = ListUtil.subList((eventList), searchContainer.getStart(), searchContainer.getEnd());
			%>

			<liferay-ui:search-container-results>

				<%
				searchContainer.setResults(eventResults);
				searchContainer.setTotal(eventList.size());
				%>

			</liferay-ui:search-container-results>

			<!-- Snippet in META-INF/resources/WEB-INF/tags -->
			<liferay-ui:search-container-row
				className="com.liferay.training.amf.monitor.model.Event"
				modelVar="event"
			>

				<%
				String eventVal =
						event.getEventDate() + " " +
								event.getScreenName() + " " +
								"(" + event.getUserId() + ")" + " " +
								event.getIpAddress() + " " +
								event.getEventType();
				%>

				<liferay-ui:search-container-column-text name="Event" value="<%= eventVal %>" />
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</liferay-ui:section>

	<!-- Registration, show only registration events -->
	<liferay-ui:section>
		<% request.setAttribute("tab", "Registration"); %>

		<%
		List regList = new ArrayList();

		for (Object o : eventList) {
			Event e = (Event)o;

			if (e.getEventType().equals("Registration")) {
				regList.add(o);
			}
		}
		%>

		<liferay-ui:search-container
			delta="20"
			deltaConfigurable="false"
			emptyResultsMessage="No more items"
			iteratorURL="<%= portletURL %>"
		>

			<%
			List eventResults = ListUtil.subList((regList), searchContainer.getStart(), searchContainer.getEnd());
			%>

			<liferay-ui:search-container-results>

				<%
				searchContainer.setResults(eventResults);
				searchContainer.setTotal(regList.size());
				%>

			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.training.amf.monitor.model.Event"
				modelVar="event"
			>

				<%
				String eventVal =
						event.getEventDate() + " " +
								event.getScreenName() + " " +
								"(" + event.getUserId() + ")" + " " +
								event.getIpAddress() + " " +
								event.getEventType();
				%>

				<liferay-ui:search-container-column-text name="Event" value="<%= eventVal %>" />
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</liferay-ui:section>

	<!-- Login, show only login events -->
	<liferay-ui:section>
		<% request.setAttribute("tab", "Login"); %>

		<%
		List logList = new ArrayList();

		for (Object o : eventList) {
			Event e = (Event)o;

			if (e.getEventType().equals("Login")) {
				logList.add(o);
			}
		}
		%>

		<liferay-ui:search-container
			delta="20"
			deltaConfigurable="false"
			emptyResultsMessage="No more items"
			iteratorURL="<%= portletURL %>"
		>

			<%
			List eventResults = ListUtil.subList((logList),
			searchContainer.getStart(),
			searchContainer.getEnd());
			%>

			<liferay-ui:search-container-results>

				<%
				searchContainer.setResults(eventResults);
				searchContainer.setTotal(logList.size());
				%>

			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.training.amf.monitor.model.Event"
				modelVar="event"
			>

				<%
				String eventVal =
						event.getEventDate() + " " +
						event.getScreenName() + " " +
						"(" + event.getUserId() + ")" + " " +
						event.getIpAddress() + " " +
						event.getEventType();
				%>

				<liferay-ui:search-container-column-text name="Event" value="<%= eventVal %>" />
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</liferay-ui:section>
</liferay-ui:tabs>