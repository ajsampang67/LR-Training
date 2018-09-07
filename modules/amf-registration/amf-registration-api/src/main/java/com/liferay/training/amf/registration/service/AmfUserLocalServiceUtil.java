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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AmfUser. This utility wraps
 * {@link com.liferay.training.amf.registration.service.impl.AmfUserLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AmfUserLocalService
 * @see com.liferay.training.amf.registration.service.base.AmfUserLocalServiceBaseImpl
 * @see com.liferay.training.amf.registration.service.impl.AmfUserLocalServiceImpl
 * @generated
 */
@ProviderType
public class AmfUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.training.amf.registration.service.impl.AmfUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link
	* AmfUserLocalServiceUtil} to
	* access the amf user local service.
	*/
	public static void addAmfUser(javax.portlet.ActionRequest request,
		javax.portlet.ActionResponse response,
		java.util.List<java.lang.String> errors, long companyId,
		long creatorUserId, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress,
		java.lang.String userName, int b_month, int b_day, int b_year,
		int male, java.lang.String password1, java.lang.String password2,
		java.lang.String homePhone, java.lang.String mobilePhone,
		java.lang.String street1, java.lang.String street2,
		java.lang.String city, long regionId, long countryId,
		java.lang.String zip, java.lang.String secQ, java.lang.String secA,
		boolean tou) {
		getService()
			.addAmfUser(request, response, errors, companyId, creatorUserId,
			firstName, lastName, emailAddress, userName, b_month, b_day,
			b_year, male, password1, password2, homePhone, mobilePhone,
			street1, street2, city, regionId, countryId, zip, secQ, secA, tou);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AmfUserLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AmfUserLocalService, AmfUserLocalService> _serviceTracker =
		ServiceTrackerFactory.open(AmfUserLocalService.class);
}