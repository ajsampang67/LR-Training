<portlet:param name="tabs1" value="<%= AmfEventMonitorPortletKeys.ALL %>" />

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", AmfEventMonitorPortletKeys.ALL);
tabParam = AmfEventMonitorPortletKeys.ALL;
%>

<%@ include file="/_monitor-search-container.jsp" %>