package com.liferay.training.amf.monitor;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.training.amf.monitor.constants.AmfEventMonitorPortletKeys;
import com.liferay.training.amf.monitor.service.EventLocalService;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alfred Sampang
 */
@Component(
	immediate = true, property = {"key=login.events.post"},
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
				Group group = _groupLocalService.getGroup(
					companyId, GroupConstants.GUEST);

				groupId = group.getGroupId();
			}
			catch (PortalException pe) {
				pe.printStackTrace();
			}

			String ipAddress = user.getLoginIP();
			String eventType = AmfEventMonitorPortletKeys.LOGIN;

			_eventLocalService.addEvent(
				companyId, groupId, user, ipAddress, eventType);
		}
		catch (PortalException pe) {
			pe.printStackTrace();
		}
	}

	@Reference
	private EventLocalService _eventLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}