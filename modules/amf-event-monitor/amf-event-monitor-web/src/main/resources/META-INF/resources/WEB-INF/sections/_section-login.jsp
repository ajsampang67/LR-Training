			<portlet:param name="tabs1" value="<%= AmfEventMonitorPortletKeys.LOGIN %>" />

			<%
			PortletURL portletURL = renderResponse.createRenderURL();
			portletURL.setParameter("tabs1", AmfEventMonitorPortletKeys.LOGIN);
			tabParam = AmfEventMonitorPortletKeys.LOGIN; %>

			<%
			List logList = new ArrayList();

			for (Object o : eventList) {
				Event e = (Event)o;

				if (e.getEventType().equals(AmfEventMonitorPortletKeys.LOGIN)) {
					logList.add(o);
				}
			}

			displayList = logList;
			%>

			<%@ include file="/_monitor-search-container.jsp" %>