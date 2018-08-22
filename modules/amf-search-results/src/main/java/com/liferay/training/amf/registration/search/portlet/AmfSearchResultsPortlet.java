package com.liferay.training.amf.registration.search.portlet;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.training.amf.registration.search.constants.AmfSearchResultsPortletKeys;

import java.io.IOException;

import javax.portlet.*;

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
		"javax.portlet.name=" + AmfSearchResultsPortletKeys.AmfSearchResults,
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-processing-event=ipc.search;localhost"
	},
	service = Portlet.class
)
public class AmfSearchResultsPortlet extends GenericPortlet {

	@ProcessEvent(qname ="{localhost}ipc.search")
	public void arrivalDestination(
		EventRequest request, EventResponse response) {

		Event event = request.getEvent();

		String zip = (String)event.getValue();
		String error = "";

		boolean isEmpty = Validator.isNull(zip);
		boolean isNum = Validator.isNumber(zip);

		boolean isFive = false;

		if (zip.length() == 5) {
			isFive = true;
		}

		// Check that the zip code is valid

		if (isEmpty || !isNum || !isFive) {
			error = "Please enter a valid 5-digit zip code";
		}

		response.setRenderParameter("error", error);
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
			getPortletContext().getRequestDispatcher("/META-INF/resources/view.jsp");

		String zip = ParamUtil.getString(renderRequest, "zip");
		String error = ParamUtil.getString(renderRequest, "error");

		// Send error if there is one, otherwise show "Search results for ..."

		if (Validator.isNotNull(error))renderResponse.setTitle(error);
		else
			renderResponse.setTitle(
				"Search Results for ".concat(HtmlUtil.escape(zip)));

		requestDispatcher.include(renderRequest, renderResponse);
	}

}