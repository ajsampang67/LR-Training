package com.liferay.training.amf.registration.commands;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.training.amf.registration.constants.RegistrationPortletKeys;
import com.liferay.training.amf.registration.service.AmfUserLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.ArrayList;
import java.util.List;

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
			creatorUserId = _userLocalService.getDefaultUserId(companyId);
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
			countryId = _countryService.getCountryByA2("US").getCountryId();
		} catch (PortalException e) {
			_log.error(e);
		}

		String zip = ParamUtil.getString(request, "zip");

		String secQ = ParamUtil.getString(request, "security_question");
		String secA = ParamUtil.getString(request, "security_answer");
		boolean tou = ParamUtil.getBoolean(request, "accepted_tou");


        _amfUserLocalService.addAmfUser(request, response, errors,
                companyId, creatorUserId, firstName, lastName, emailAddress,
                userName, b_month, b_day, b_year, male, password1,
                password2, homePhone, mobilePhone, street1, street2, city,
                regionId, countryId, zip, secQ, secA, tou);


	}

	@Reference
	AmfUserLocalService _amfUserLocalService;
	@Reference
	UserLocalService _userLocalService;
	@Reference
	CountryService _countryService;


	private static final Log _log = LogFactoryUtil.getLog(
		processFormMVCActionCommand.class);

}