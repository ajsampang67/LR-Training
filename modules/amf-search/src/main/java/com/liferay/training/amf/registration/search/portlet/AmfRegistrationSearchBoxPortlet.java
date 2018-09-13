package com.liferay.training.amf.registration.search.portlet;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.training.amf.registration.search.constants.AmfRegistrationSearchBoxPortletKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.xml.namespace.QName;

import org.osgi.service.component.annotations.Component;

/**
 * @author liferay
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=AMF",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Enter US zip",
		"javax.portlet.name=" +
			AmfRegistrationSearchBoxPortletKeys.AMF_REGISTRATION_SEARCH_BOX,
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-publishing-event=ipc.search;localhost"

	},
	service = Portlet.class
)
public class AmfRegistrationSearchBoxPortlet extends GenericPortlet {

	@Override
	protected void doView(
		RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {
		PortletRequestDispatcher requestDispatcher =
			getPortletContext().getRequestDispatcher("/META-INF/resources/view.jsp");

		requestDispatcher.include(renderRequest, renderResponse);
	}

	@Override
	public void processAction(ActionRequest request, ActionResponse response)
		throws IOException, PortletException {
		String zip = ParamUtil.getString(request, "zip");
		QName qName = new QName("localhost", "ipc.search");

		response.setEvent(qName, zip);
	}

}