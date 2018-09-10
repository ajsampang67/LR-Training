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

	<%
	PortletURL portletURL = renderResponse.createRenderURL();
	int eventCount = EventLocalServiceUtil.getEventsCount();

	// Checking if user has permission to see all activity

	List unfilteredEventList = EventLocalServiceUtil.getEvents(0, eventCount);

	/** 1)Grab portlet, check for portlet-resource permission
	 *   VIEW-ALL-EVENTS
	 *  2) If user does not have permission to view all events, parse
	 *      list to grab only the events user created themselves
	 *  3) Else user has permission to view all events, just show all
	 */
	List eventList = new ArrayList();

	if (!permissionChecker.hasPermission(
        scopeGroupId, portletDisplay.getRootPortletId(),
        portletDisplay.getResourcePK(), "VIEW_ALL_EVENTS")) {
		for (Object obj : unfilteredEventList) {
			Event event = (Event)obj;
			if (event.getUserId() == user.getUserId())
				eventList.add(obj);
		}
	}
	else {
		eventList = unfilteredEventList;
	}
	List displayList = eventList;
	%>

	<!-- All section, show everything up to 20 items per page -->
	<liferay-ui:section>
		<% request.setAttribute("tab", "All"); %>
        <%@ include file="/_monitor-search-container.jsp"%>
	</liferay-ui:section>

	<!-- Registration section, show only registration events -->
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
		displayList = regList;
		%>

		<%@ include file="/_monitor-search-container.jsp"%>
	</liferay-ui:section>

	<!-- Login section, show only login events -->
	<liferay-ui:section>
		<% request.setAttribute("tab", "Login"); %>

		<%
		List logList = new ArrayList();

		for (Object o : eventList) {
			Event e = (Event)o;

			System.out.println(e.getEventType());
			if (e.getEventType().equals("Login")) {
				logList.add(o);
			}
		}
		displayList = logList;

		%>

		<%@ include file="/_monitor-search-container.jsp"%>
	</liferay-ui:section>
</liferay-ui:tabs>