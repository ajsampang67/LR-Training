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

package com.liferay.training.amf.monitor.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.training.amf.monitor.model.Event;
import com.liferay.training.amf.monitor.service.base.EventLocalServiceBaseImpl;
import com.liferay.training.amf.monitor.util.ActionKeys;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * The implementation of the event local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.training.amf.monitor.service.EventLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EventLocalServiceBaseImpl
 * @see EventLocalServiceUtil
 */
public class EventLocalServiceImpl extends EventLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use
	 * {@link EventLocalServiceUtil} to access the event local service.
	 */
	public void addEvent(
		long companyId, long groupId, User user, String ipAddress,
		String eventType) {

		Calendar eventCalendar = CalendarFactoryUtil.getCalendar();

		Date eventDate = eventCalendar.getTime();

		String new_format = "yyyy-MM-dd kk:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(new_format);

		String formattedEventDate = sdf.format(eventDate);

		long eventId = counterLocalService.increment(Event.class.getName());

		Event newEvent = eventLocalService.createEvent(eventId);

		newEvent.setCompanyId(companyId);
		newEvent.setGroupId(groupId);

		newEvent.setEventDate(formattedEventDate);
		newEvent.setUserId(user.getUserId());
		newEvent.setScreenName(user.getScreenName());
		newEvent.setIpAddress(ipAddress);
		newEvent.setEventType(eventType);

		String[] guestPermissions = {};

		try {
			resourceLocalService.addResources(
				companyId, groupId, user.getUserId(), Event.class.getName(),
				eventId, true, false, false);

			resourceLocalService.addModelResources(
				companyId, groupId, user.getUserId(), Event.class.getName(),
				eventId, new String[] {ActionKeys.VIEW}, guestPermissions);
		}
		catch (PortalException pe) {
			pe.printStackTrace();
		}

		eventLocalService.addEvent(newEvent);
	}

}