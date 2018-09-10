<portlet:param name="tabs1" value="<%= AmfEventMonitorPortletKeys.REGISTRATION %>" />

<%
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("tabs1", AmfEventMonitorPortletKeys.REGISTRATION);
tabParam = AmfEventMonitorPortletKeys.REGISTRATION;
%>

<%
List regList = new ArrayList();

for (Object o : eventList) {
	Event e = (Event)o;

	if (e.getEventType().equals(AmfEventMonitorPortletKeys.REGISTRATION)) {
		regList.add(o);
	}
}

displayList = regList;
%>

<%@ include file="/_monitor-search-container.jsp" %>