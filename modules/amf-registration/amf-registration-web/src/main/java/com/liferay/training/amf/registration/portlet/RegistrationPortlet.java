package com.liferay.training.amf.registration.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.training.amf.registration.constants.RegistrationPortletKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alfred Sampang
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=AMF",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=AMF Registration",
		"javax.portlet.name="+ RegistrationPortletKeys.REGISTRATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class RegistrationPortlet extends MVCPortlet {

	/**
	 * Function to process the registration form submission
	 */
	@Override
	public void doView(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		PortletRequestDispatcher requestDispatcher =
			getPortletContext().getRequestDispatcher("/META-INF/resources/view.jsp");

		requestDispatcher.include(request, response);
	}

}