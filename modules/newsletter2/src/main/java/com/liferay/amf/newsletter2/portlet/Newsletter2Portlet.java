package com.liferay.amf.newsletter2.portlet;

import com.liferay.amf.newsletter2.constants.newsletter2PortletKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;

import static com.liferay.amf.newsletter2.portlet.AmfNewsletterHelpers.getArticleFieldValue;
import static com.liferay.amf.newsletter2.portlet.AmfNewsletterHelpers.getMonthDisplayName;

/**
 * @author liferay
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=newsletter2",
		"javax.portlet.name=" + newsletter2PortletKeys.newsletter2,
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Newsletter2Portlet extends GenericPortlet {
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {

		System.out.println("in doView");

		PortletRequestDispatcher requestDispatcher =
				getPortletContext().getRequestDispatcher("/listing.jsp");

		requestDispatcher.include(request, response);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		System.out.println("In render");

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();
		long groupId = themeDisplay.getSiteGroupId();

		List yearsList = new ArrayList();

		HashMap<String, List<JournalArticle>> issuesByYear = new HashMap<>();
		HashMap<JournalArticle, List<JournalArticle>> articlesByIssue =
				new HashMap<>();
		HashMap<String, List<String>> monthsByYear = new HashMap<>();

		// Get journal articles that are published only

		List allJournalArticles = JournalArticleLocalServiceUtil.getArticles(
				groupId, 0, 0, 0,
				JournalArticleLocalServiceUtil.getArticlesCount(groupId, 0, 0));
		List journalArticles = new ArrayList();

		//TODO Dynamically get Ids
		final String IssueDDMKey = "33255";
		final String ArticleDDMKey = "33251";

		// Filter for most recent versions

		for (Object object : allJournalArticles) {
			JournalArticle ja = (JournalArticle)object;
			try {
				JournalArticle newestVersion =
						JournalArticleLocalServiceUtil.getLatestArticle(
								ja.getResourcePrimKey());

				if (!journalArticles.contains(newestVersion)) {
					journalArticles.add(newestVersion);
				}
			} catch (PortalException e) {
				e.printStackTrace();
			}
		}

		ArrayList articles = new ArrayList();
		ArrayList issues = new ArrayList();

		for (Object object : journalArticles) {
			JournalArticle journalArticle = (JournalArticle)object;


			switch(journalArticle.getDDMStructureKey()) {
				case IssueDDMKey:
					String issueDateStr = getArticleFieldValue(
							journalArticle, "IssueDate");
					String issueYear = issueDateStr.substring(0, 4);
					String issueMonth = getMonthDisplayName(issueDateStr);

					yearsList.add(issueYear);

					if (monthsByYear.containsKey(issueYear)) {
						monthsByYear.get(issueYear).add(issueMonth);
					} else {
						monthsByYear.put(issueYear, new ArrayList<>());
						monthsByYear.get(issueYear).add(issueMonth);
					}

					if (issuesByYear.containsKey(issueYear)) {
						issuesByYear.get(issueYear).add(journalArticle);
					} else {
						monthsByYear.put(issueYear, new ArrayList<>());
						monthsByYear.get(issueYear).add(issueMonth);
					}

					break;
				case ArticleDDMKey:

					break;
				default:

					break;
			}
		} // End forEach asset

		String[] yearsArray = (String[])yearsList.toArray(
				new String[yearsList.size()]);

		System.out.println(yearsArray);
		renderRequest.setAttribute("issuesByYear", issuesByYear);
		renderRequest.setAttribute("monthsByYear", monthsByYear);
		renderRequest.setAttribute("years", yearsArray);

		// renderRequest.setAttribute("articlesByIssue", articlesByIssue);

		super.render(renderRequest, renderResponse);
	}

}