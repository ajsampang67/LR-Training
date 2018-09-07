package com.liferay.training.amf.monitor;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.training.amf.monitor.service.EventLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;

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
				groupId = _groupLocalService.getGroup(
					companyId, GroupConstants.GUEST).getGroupId();
			} catch (PortalException e) {
				e.printStackTrace();
			}

			String ipAddress = user.getLoginIP();
			String eventType = "Login";

			_eventLocalService.addEvent(
				companyId, groupId, user, ipAddress, eventType);
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}
	@Reference
	private GroupLocalService _groupLocalService;
	@Reference
	private EventLocalService _eventLocalService;

}