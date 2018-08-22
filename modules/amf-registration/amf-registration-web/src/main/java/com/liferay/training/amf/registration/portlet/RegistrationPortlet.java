package com.liferay.training.amf.registration.portlet;

import static com.liferay.training.amf.registration.validator.RegistrationValidator.acceptedTOU;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidAddress;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidBirthday;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidEmail;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidFName;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidLName;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidPassword;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidPhone;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidSecurityQ;
import static com.liferay.training.amf.registration.validator.RegistrationValidator.isValidUsername;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.training.amf.registration.constants.RegistrationPortletKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
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
	@Override
	public void processAction(ActionRequest request, ActionResponse response) {

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

		// TODO: Knock this all into one function in Validator

		isValidFName(firstName, errors);
		isValidLName(lastName, errors);
		isValidEmail(emailAddress, errors);
		isValidUsername(userName, errors);
		isValidBirthday(b_month, b_day, b_year, errors);
		isValidPassword(password1, password2, errors);
		isValidAddress(street1, street2, city, regionId, zip, errors);
		isValidSecurityQ(secQ, secA, errors);
		acceptedTOU(tou, errors);

		// Phone validation

		if (Validator.isNotNull(homePhone)) {
			isValidPhone(homePhone, errors);
		}

		if (Validator.isNotNull(mobilePhone)) {
			isValidPhone(mobilePhone, errors);
		}

		// If no validator returns error, register user and address

		// TODO: Add this all as one function in service layer

		if (errors.size() == 0) {
			User newUser;

			// Try adding User

			try {
				Group guest = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);
				long[] organizationIds = {};
				long[] groupIds = {guest.getGroupId()};
				long[] roleIds = {};
				long[] userGroupIds = {};

				newUser = UserLocalServiceUtil.addUser(
					creatorUserId, companyId, false, password1, password2,
					false, userName, emailAddress, 0, StringPool.BLANK,
					Locale.US, firstName, StringPool.BLANK, lastName, 0, 0,
					male == 1, b_month, b_day, b_year, StringPool.BLANK,
					groupIds, organizationIds, roleIds, userGroupIds, true,
					null);

				newUser.setPasswordReset(false);
				newUser.setReminderQueryQuestion(secQ);
				newUser.setReminderQueryAnswer(secA);
				newUser.setAgreedToTermsOfUse(tou);

				// Adding phones

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(request);

				if (Validator.isNotNull(homePhone)) {
					PhoneLocalServiceUtil.addPhone(
							newUser.getUserId(), Contact.class.getName(),
							newUser.getContactId(), homePhone, StringPool.BLANK,
							11008, true, serviceContext);
				}

				if (Validator.isNotNull(mobilePhone)) {
					PhoneLocalServiceUtil.addPhone(
							newUser.getUserId(), Contact.class.getName(),
							newUser.getContactId(), mobilePhone,
							StringPool.BLANK, 11011, true, serviceContext);
				}

				// Adding address

				long typeId = 11000;

				AddressLocalServiceUtil.addAddress(
						newUser.getUserId(), Contact.class.getName(),
						newUser.getContactId(), street1, street2,
						StringPool.BLANK, city, zip, regionId, countryId,
						typeId, true, true, serviceContext);
				UserLocalServiceUtil.updateUser(newUser);

				errors.add("Registration Successful");
			} catch (PortalException e) {
				e.printStackTrace();

				// Catching duplicate username/email in DB

				Class<?> emailDup =
					com.liferay.portal.kernel.exception.UserEmailAddressException.MustNotBeDuplicate.class;
				Class<?> userDup =
					com.liferay.portal.kernel.exception.UserScreenNameException.MustNotBeDuplicate.class;

				if (e.getClass() == emailDup) {
					errors.add("Email already in use");
				} else if (e.getClass() == userDup) {
					errors.add("Username already in use");
				}

				// Used to keep forms filled after failed validation

				PortalUtil.copyRequestParameters(request, response);
				errors.add("Registration Failed");
			}
		}

		response.setRenderParameter("actionResult", errors.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationPortlet.class);

}