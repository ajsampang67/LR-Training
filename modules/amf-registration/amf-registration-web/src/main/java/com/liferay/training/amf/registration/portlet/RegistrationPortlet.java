package com.liferay.training.amf.registration.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.training.amf.registration.constants.RegistrationPortletKeys;

import javax.portlet.Portlet;

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
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + RegistrationPortletKeys.REGISTRATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"mvc.command.name=register"
	},
	service = Portlet.class
)
public class RegistrationPortlet extends MVCPortlet {

	/**
	 * Function to process the registration form submission
	 *
	 * @param request  - request from form
	 * @param response - response, including form params and
	 *                 errors or success message
	 */
	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationPortlet.class);

}