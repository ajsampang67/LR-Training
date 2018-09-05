package com.liferay.training.amf.monitor;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.training.amf.monitor.service.EventLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true, property={"key=login.events.post"},
		service = LifecycleAction.class
)
public class AmfLoginMonitor implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		HttpServletRequest req = lifecycleEvent.getRequest();
		User user = null;

		try {
			user = PortalUtil.getUser(req);
			long companyId = PortalUtil.getDefaultCompanyId();
			long groupId = 0;
			try {
				groupId = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST).getGroupId();
			} catch (PortalException e) {
				e.printStackTrace();
			}

			String ipAddress = user.getLoginIP();
			String eventType = "Login";

			EventLocalServiceUtil.addEvent(
				companyId, groupId, user, ipAddress, eventType);
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}

}