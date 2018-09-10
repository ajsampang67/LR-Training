<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", AmfEventMonitorPortletKeys.ALL);
%>

<liferay-ui:tabs
	names= "<%=
		AmfEventMonitorPortletKeys.ALL + "," +
		AmfEventMonitorPortletKeys.REGISTRATION + "," +
		AmfEventMonitorPortletKeys.LOGIN
	%>"
	param="tabs1" refresh="<%= false %>" type="tabs nav-tabs-default"
	tabsValues= "<%=
		AmfEventMonitorPortletKeys.ALL + "," +
		AmfEventMonitorPortletKeys.REGISTRATION + "," +
		AmfEventMonitorPortletKeys.LOGIN
	%>"
>

	<%
	int eventCount = EventLocalServiceUtil.getEventsCount();

	// Checking if user has permission to see all activity

	List unfilteredEventList = EventLocalServiceUtil.getEvents(0, eventCount);

	/** 1)Grab portlet, check for portlet-resource permission
	 *  2) If user does not have permission to view all events, parse
	 *      list to grab only the events user created themselves
	 *  3) Else user has permission to view all events, just show all
	 */
	List eventList = new ArrayList();

	if (!permissionChecker.hasPermission(scopeGroupId, portletDisplay.getRootPortletId(), portletDisplay.getResourcePK(), AmfEventMonitorPortletKeys.VIEW_ALL_EVENTS)) {
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
	String tabParam = AmfEventMonitorPortletKeys.ALL;
	%>

	<!-- All section, show everything up to 20 items per page -->
	<liferay-ui:section>
		<%@ include file="WEB-INF/sections/_section-all.jsp" %>
	</liferay-ui:section>

	<!-- Registration section, show only registration events -->
	<liferay-ui:section>
		<%@ include file="WEB-INF/sections/_section-registration.jsp" %>
	</liferay-ui:section>

	<!-- Login section, show only login events -->
	<liferay-ui:section>
		<%@ include file="WEB-INF/sections/_section-login.jsp" %>
	</liferay-ui:section>
</liferay-ui:tabs>