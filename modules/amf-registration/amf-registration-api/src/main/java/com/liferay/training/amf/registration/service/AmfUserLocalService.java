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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * Provides the local service interface for AmfUser. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AmfUserLocalServiceUtil
 * @see com.liferay.training.amf.registration.service.base.AmfUserLocalServiceBaseImpl
 * @see com.liferay.training.amf.registration.service.impl.AmfUserLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AmfUserLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AmfUserLocalServiceUtil} to access the amf user local service. Add custom service methods to {@link com.liferay.training.amf.registration.service.impl.AmfUserLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addAmfUser(ActionRequest request, ActionResponse response,
		List<java.lang.String> errors, long companyId, long creatorUserId,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String emailAddress, java.lang.String userName, int b_month,
		int b_day, int b_year, int male, java.lang.String password1,
		java.lang.String password2, java.lang.String homePhone,
		java.lang.String mobilePhone, java.lang.String street1,
		java.lang.String street2, java.lang.String city, long regionId,
		long countryId, java.lang.String zip, java.lang.String secQ,
		java.lang.String secA, boolean tou);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();
}