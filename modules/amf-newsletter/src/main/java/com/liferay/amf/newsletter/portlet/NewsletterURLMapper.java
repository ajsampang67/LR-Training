package com.liferay.amf.newsletter.portlet;

import com.liferay.amf.newsletter.constants.NewsletterPortletKeys;
import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alfred Sampang
 */
@Component(
	property = {
		"com.liferay.portlet.friendly-url-routes=META-INF/resources/friendly-url-routes/routes.xml",
		"javax.portlet.name=" + NewsletterPortletKeys.NEWSLETTER_PORTLET_NAME
	},
	service = FriendlyURLMapper.class
)
public class NewsletterURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String getMapping() {
		return "article-issue";
	}

}