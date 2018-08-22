package com.liferay.training.amf.monitor;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

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
		"javax.portlet.display-name=AMF Event Monitor",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class AmfEventMonitorPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		PortletRequestDispatcher requestDispatcher =
			getPortletContext().getRequestDispatcher("/META-INF/resources/view.jsp");

		requestDispatcher.include(request, response);
	}

}