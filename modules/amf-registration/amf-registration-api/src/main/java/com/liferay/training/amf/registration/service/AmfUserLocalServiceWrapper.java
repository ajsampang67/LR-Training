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

package com.liferay.training.amf.registration.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AmfUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AmfUserLocalService
 * @generated
 */
@ProviderType
public class AmfUserLocalServiceWrapper implements AmfUserLocalService,
	ServiceWrapper<AmfUserLocalService> {
	public AmfUserLocalServiceWrapper(AmfUserLocalService amfUserLocalService) {
		_amfUserLocalService = amfUserLocalService;
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link
	* AmfUserLocalServiceUtil} to
	* access the amf user local service.
	*/
	@Override
	public void addAmfUser(javax.portlet.ActionRequest request,
		javax.portlet.ActionResponse response,
		java.util.List<java.lang.String> errors, long companyId,
		long creatorUserId, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress,
		java.lang.String userName, int b_month, int b_day, int b_year,
		int male, java.lang.String password1, java.lang.String password2,
		java.lang.String homePhone, java.lang.String mobilePhone,
		java.lang.String street1, java.lang.String street2,
		java.lang.String city, long regionId, long countryId,
		java.lang.String zip, java.lang.String securityQuestion, java.lang.String securityAnswer,
		boolean tou) throws PortalException {
		_amfUserLocalService.addAmfUser(request, response, errors, companyId,
			creatorUserId, firstName, lastName, emailAddress, userName,
			b_month, b_day, b_year, male, password1, password2, homePhone,
			mobilePhone, street1, street2, city, regionId, countryId, zip,
			securityQuestion, securityAnswer, tou);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _amfUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public AmfUserLocalService getWrappedService() {
		return _amfUserLocalService;
	}

	@Override
	public void setWrappedService(AmfUserLocalService amfUserLocalService) {
		_amfUserLocalService = amfUserLocalService;
	}

	private AmfUserLocalService _amfUserLocalService;
}