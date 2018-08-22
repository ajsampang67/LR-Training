package com.liferay.training.amf.monitor;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.training.amf.monitor.service.EventLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = ModelListener.class)
public class AmfListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
		long companyId = PortalUtil.getDefaultCompanyId();
		long groupId = 0;
		try {
			groupId = GroupLocalServiceUtil.getGroup(
				companyId, GroupConstants.GUEST).getGroupId();
		} catch (PortalException e) {
			e.printStackTrace();
		}

		String ipAddress = "0.0.0.0";
		String eventType = ("Registration");

		EventLocalServiceUtil.addEvent(
			companyId, groupId, user, ipAddress, eventType);
	}

}