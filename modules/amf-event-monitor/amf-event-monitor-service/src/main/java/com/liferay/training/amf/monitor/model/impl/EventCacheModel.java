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

package com.liferay.training.amf.monitor.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import com.liferay.training.amf.monitor.model.Event;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Event in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Event
 * @generated
 */
@ProviderType
public class EventCacheModel implements CacheModel<Event>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EventCacheModel)) {
			return false;
		}

		EventCacheModel eventCacheModel = (EventCacheModel)obj;

		if (EventId == eventCacheModel.EventId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, EventId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", EventId=");
		sb.append(EventId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", eventDate=");
		sb.append(eventDate);
		sb.append(", screenName=");
		sb.append(screenName);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", ipAddress=");
		sb.append(ipAddress);
		sb.append(", eventType=");
		sb.append(eventType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Event toEntityModel() {
		EventImpl eventImpl = new EventImpl();

		if (uuid == null) {
			eventImpl.setUuid("");
		}
		else {
			eventImpl.setUuid(uuid);
		}

		eventImpl.setEventId(EventId);
		eventImpl.setGroupId(groupId);
		eventImpl.setCompanyId(companyId);

		if (eventDate == null) {
			eventImpl.setEventDate("");
		}
		else {
			eventImpl.setEventDate(eventDate);
		}

		if (screenName == null) {
			eventImpl.setScreenName("");
		}
		else {
			eventImpl.setScreenName(screenName);
		}

		eventImpl.setUserId(userId);

		if (ipAddress == null) {
			eventImpl.setIpAddress("");
		}
		else {
			eventImpl.setIpAddress(ipAddress);
		}

		if (eventType == null) {
			eventImpl.setEventType("");
		}
		else {
			eventImpl.setEventType(eventType);
		}

		eventImpl.resetOriginalValues();

		return eventImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		EventId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		eventDate = objectInput.readUTF();
		screenName = objectInput.readUTF();

		userId = objectInput.readLong();
		ipAddress = objectInput.readUTF();
		eventType = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(EventId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		if (eventDate == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(eventDate);
		}

		if (screenName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(screenName);
		}

		objectOutput.writeLong(userId);

		if (ipAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(ipAddress);
		}

		if (eventType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(eventType);
		}
	}

	public String uuid;
	public long EventId;
	public long groupId;
	public long companyId;
	public String eventDate;
	public String screenName;
	public long userId;
	public String ipAddress;
	public String eventType;
}