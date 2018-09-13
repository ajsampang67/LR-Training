/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.training.amf.registration.service.impl;

import static com.liferay.training.amf.registration.service.validator.RegistrationValidator.isValidPhone;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.training.amf.registration.service.base.AmfUserLocalServiceBaseImpl;
import com.liferay.training.amf.registration.service.validator.RegistrationValidator;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * The implementation of the amf user local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the {@link
 * com.liferay.training.amf.registration.service.AmfUserLocalService} interface.
 * <p> This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    AmfUserLocalServiceBaseImpl
 */
public class AmfUserLocalServiceImpl extends AmfUserLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link
	 * com.liferay.training.amf.registration.service.AmfUserLocalServiceUtil} to
	 * access the amf user local service.
	 */
	public void addAmfUser(
		ActionRequest request, ActionResponse response, List<String> errors,
		long companyId, long creatorUserId, String firstName, String lastName,
		String emailAddress, String userName, int b_month, int b_day,
		int b_year, int male, String password1, String password2,
		String homePhone, String mobilePhone, String street1, String street2,
		String city, long regionId, long countryId, String zip, String secQ,
		String secA, boolean tou) throws PortalException {

		// Validating all form data

		RegistrationValidator.isValidForm(
			errors, firstName, lastName, emailAddress, userName, b_month, b_day,
			b_year, password1, password2, street1, street2, city, regionId, zip,
			secQ, secA, tou);

		// Phone validation

		if (Validator.isNotNull(homePhone)) {
			isValidPhone(homePhone, errors);
		}

		if (Validator.isNotNull(mobilePhone)) {
			isValidPhone(mobilePhone, errors);
		}

		for (String error : errors) {
			SessionErrors.add(request, error);
		}

		if (SessionErrors.isEmpty(request)) {
			User newUser;
			Group guest = null;
			try {
				guest = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);
			}
			catch (PortalException pe) {
				pe.printStackTrace();
			}

			long[] organizationIds = {};
			long[] groupIds = {guest.getGroupId()};
			long[] roleIds = {};
			long[] userGroupIds = {};

			newUser = UserLocalServiceUtil.addUser(
				creatorUserId, companyId, false, password1, password2, false,
				userName, emailAddress, 0, StringPool.BLANK, Locale.US,
				firstName, StringPool.BLANK, lastName, 0, 0, male == 1, b_month,
				b_day, b_year, StringPool.BLANK, groupIds, organizationIds,
				roleIds, userGroupIds, true, null);

			newUser.setPasswordReset(false);
			newUser.setReminderQueryQuestion(secQ);
			newUser.setReminderQueryAnswer(secA);
			newUser.setAgreedToTermsOfUse(tou);

			// Adding phones

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				request);

			if (Validator.isNotNull(homePhone)) {
				PhoneLocalServiceUtil.addPhone(
					newUser.getUserId(), Contact.class.getName(),
					newUser.getContactId(), homePhone, StringPool.BLANK, 11008,
					true, serviceContext);
			}

			if (Validator.isNotNull(mobilePhone)) {
				PhoneLocalServiceUtil.addPhone(
					newUser.getUserId(), Contact.class.getName(),
					newUser.getContactId(), mobilePhone, StringPool.BLANK,
					11011, true, serviceContext);
			}

			long typeId = 11000;

			AddressLocalServiceUtil.addAddress(
				newUser.getUserId(), Contact.class.getName(),
				newUser.getContactId(), street1, street2, StringPool.BLANK,
				city, zip, regionId, countryId, typeId, true, true,
				serviceContext);

			UserLocalServiceUtil.updateUser(newUser);
		}
		else {
			PortalUtil.copyRequestParameters(request, response);
		}
	}

}