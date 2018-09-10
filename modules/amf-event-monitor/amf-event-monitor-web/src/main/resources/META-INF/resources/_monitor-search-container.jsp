    <liferay-ui:search-container
			delta="20"
			deltaConfigurable="false"
			emptyResultsMessage="No more items"
			iteratorURL="<%= portletURL %>"
		>

			<%-- Parse list for pages --%>

			<%
			List eventResults = ListUtil.subList((displayList), searchContainer.getStart(), searchContainer.getEnd());
			%>

			<liferay-ui:search-container-results>

				<%
				searchContainer.setResults(eventResults);
				searchContainer.setTotal(displayList.size());
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
