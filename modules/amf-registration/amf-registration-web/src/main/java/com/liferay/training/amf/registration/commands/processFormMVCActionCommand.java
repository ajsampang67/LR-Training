package com.liferay.training.amf.registration.commands;

import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidPhone;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.training.amf.registration.constants.RegistrationPortletKeys;
import com.liferay.training.amf.registration.validator.RegistrationValidator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
		immediate = true,
		property = {
				"javax.portlet.name="+ RegistrationPortletKeys.REGISTRATION,
				"mvc.command.name=/processForm"
		},
		service = MVCActionCommand.class
)
public class processFormMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest request, ActionResponse response) throws Exception {

		// List for errors to be checked at end

		List<String> errors = new ArrayList<>();
		long companyId = PortalUtil.getDefaultCompanyId();
		long creatorUserId = 0;
		try {
			creatorUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		} catch (PortalException e) {
			_log.fatal(e);
		}

		// Basic Info

		String firstName = ParamUtil.getString(request, "first_name");
		String lastName = ParamUtil.getString(request, "last_name");
		String emailAddress = ParamUtil.getString(request, "email_address");
		String userName = ParamUtil.getString(request, "username");

		// Birthday, male

		int b_month = ParamUtil.getInteger(request, "b_month");
		int b_day = ParamUtil.getInteger(request, "b_day");
		int b_year = ParamUtil.getInteger(request, "b_year");

		int male = ParamUtil.getInteger(request, "male");

		// Password

		String password1 = ParamUtil.getString(request, "password1");
		String password2 = ParamUtil.getString(request, "password2");

		// Phone

		String homePhone = ParamUtil.getString(request, "home_phone");
		String mobilePhone = ParamUtil.getString(request, "mobile_phone");

		// Address

		String street1 = ParamUtil.getString(request, "address");
		String street2 = ParamUtil.getString(request, "address2");
		String city = ParamUtil.getString(request, "city");
		long regionId = ParamUtil.getLong(request, "state");
		long countryId = 0;
		try {
			countryId = CountryServiceUtil.getCountryByA2("US").getCountryId();
		} catch (PortalException e) {
			_log.error(e);
		}

		String zip = ParamUtil.getString(request, "zip");

		String secQ = ParamUtil.getString(request, "security_question");
		String secA = ParamUtil.getString(request, "security_answer");
		boolean tou = ParamUtil.getBoolean(request, "accepted_tou");

		// Validating all form data

		RegistrationValidator.isValidForm(
				errors, firstName, lastName, emailAddress, userName, b_month,
b_day, b_year, password1, password2, street1, street2, city, regionId, zip,
secQ, secA, tou);

		// Phone validation

		if (Validator.isNotNull(homePhone)) {
			isValidPhone(homePhone, errors);
		}

		if (Validator.isNotNull(mobilePhone)) {
			isValidPhone(mobilePhone, errors);
		}

		// If no validator returns error, register user and address

		if (errors.size() == 0) {
			User newUser;

			/**
			AmfUserLocalServiceUtil.addAmfUser(request, response, errors,
					companyId, creatorUserId, firstName, lastName, emailAddress,
					userName, b_month, b_day, b_year, male, password1,
					password2, homePhone, mobilePhone, street1, street2, city,
					regionId, countryId, zip, secQ, secA, tou);
			 **/
		}

		for (String error : errors) {
			SessionErrors.add(request, error);
		}

		response.setRenderParameter("actionResult", errors.toString());
		super.processAction(request, response);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		processFormMVCActionCommand.class);

}