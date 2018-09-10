package com.liferay.training.amf.monitor;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.training.amf.monitor.service.EventLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alfred Sampang
 */
@Component(immediate = true, service = ModelListener.class)
public class AmfListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
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

		String ipAddress = "0.0.0.0";
		String eventType = "Registration";

		_eventLocalService.addEvent(
			companyId, groupId, user, ipAddress, eventType);
	}

	@Reference
	private EventLocalService _eventLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}