package com.liferay.training.amf.registration.search.portlet;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.training.amf.registration.search.constants.AmfSearchResultsPortletKeys;

import java.io.IOException;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author liferay
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=AMF",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=AMF Search Results",
		"javax.portlet.name=" + AmfSearchResultsPortletKeys.AMF_SEARCH_RESULTS,
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-processing-event=ipc.search;localhost"
	},
	service = Portlet.class
)
public class AmfSearchResultsPortlet extends GenericPortlet {

	@ProcessEvent(qname = "{localhost}ipc.search")
	public void arrivalDestination(
		EventRequest request, EventResponse response) {

		Event event = request.getEvent();

		String zip = (String)event.getValue();

		String error = "";

		boolean empty = Validator.isNull(zip);
		boolean num = Validator.isNumber(zip);

		boolean five = false;

		if (zip.length() == 5) {
			five = true;
		}

		// Check that the zip code is valid

		if (empty || !num || !five) {
			SessionErrors.add(request, "pleaseEnterAValid5DigitZipCode");
		}

		response.setRenderParameter("zip", zip);
	}

	@Override
	public void init() throws PortletException {
	}

	@Override
	protected void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletRequestDispatcher requestDispatcher =
			getPortletContext().getRequestDispatcher(
				"/META-INF/resources/view.jsp");

		String zip = ParamUtil.getString(renderRequest, "zip");

		// Send error if there is one, otherwise show "Search results for ..."

		if (SessionErrors.isEmpty(renderRequest)) {
			renderResponse.setTitle(
				AmfSearchResultsPortletKeys.SEARCH_RESULTS_FOR.concat(
					HtmlUtil.escape(zip)));
		}

		requestDispatcher.include(renderRequest, renderResponse);
	}

}