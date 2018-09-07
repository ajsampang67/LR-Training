package com.liferay.amf.newsletter.portlet;

import com.liferay.amf.newsletter.constants.NewsletterPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.liferay.amf.newsletter.portlet.AmfNewsletterHelpers.addToHashMapList;
import static com.liferay.amf.newsletter.portlet.AmfNewsletterHelpers.getArticleFieldValue;
import static com.liferay.amf.newsletter.portlet.AmfNewsletterHelpers.getMonthDisplayName;

/**
 * @author Alfred Sampang
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=AMF",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=AMF Newsletter",
		"javax.portlet.name=" + NewsletterPortletKeys.NEWSLETTER_PORTLET_NAME,
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NewsletterPortlet extends GenericPortlet {

	@Override
	public void doView(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		PortletRequestDispatcher requestDispatcher =
			getPortletContext().getRequestDispatcher("/listing.jsp");

		requestDispatcher.include(request, response);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();
		long groupId = themeDisplay.getSiteGroupId();

		List yearsList = new ArrayList();
		HashMap<String, List> monthsByYear = new HashMap<>();

		// <String Year+Month, [issues]>

		HashMap<String, List> issuesByYearAndMonth = new HashMap<>();
		HashMap<JournalArticle, List> articlesByIssue = new HashMap<>();

		// Get journal articles that are published only

		List allJournalArticles = _journalArticleLocalService.getArticles(
			groupId, 0, 0, 0,
			_journalArticleLocalService.getArticlesCount(groupId, 0, 0));
		List journalArticles = new ArrayList();

		//TODO Dynamically get Ids
		final String issue_DDM_KEY = "33255";
		final String article_DDM_KEY = "33251";

		// Filter for most recent versions

		for (Object object : allJournalArticles) {
			JournalArticle journalArticle = (JournalArticle)object;

			try {
				JournalArticle newestVersion =
					_journalArticleLocalService.getLatestArticle(
						journalArticle.getResourcePrimKey());

				if (!journalArticles.contains(newestVersion)) {
					journalArticles.add(newestVersion);
				}
			}
			catch (PortalException pe) {
				pe.printStackTrace();
			}
		}

		ArrayList articles = new ArrayList();
		ArrayList issues = new ArrayList();

		for (Object object : journalArticles) {
			JournalArticle journalArticle = (JournalArticle)object;

			switch (journalArticle.getDDMStructureKey()) {

				case issue_DDM_KEY:
					issues.add(journalArticle);

					break;
				case article_DDM_KEY:
					articles.add(journalArticle);

					break;
				default:
					_log.info("Not an issue/article");

					break;
			}
		} // End forEach asset

		for (Object issueObject : issues) {
			JournalArticle issue = (JournalArticle)issueObject;

			String issueNumber = getArticleFieldValue(issue, "issueNumber");

			for (Object articleObject : articles) {
				JournalArticle article = (JournalArticle)articleObject;

				String articleNumber = getArticleFieldValue(
					article, "issueNumber");

				if (issueNumber.equals(articleNumber)) {
					addToHashMapList(articlesByIssue, issue, article);
				}
			}

			// Getting dates for the UI, then populating issuesByYearAndMonth

			String issueDateStr = getArticleFieldValue(issue, "IssueDate");

			String issueYear = issueDateStr.substring(0, 4);
			String issueMonth = getMonthDisplayName(issueDateStr);

			if (!yearsList.contains(issueYear)) {
				yearsList.add(issueYear);
			}

			String yearToMonth = issueYear + issueMonth;

			// Add the month to the <Year, [Month]> hashmap

			addToHashMapList(monthsByYear, issueYear, issueMonth);

			// Add the issues to the <(String YearMonth), [issues]> hashmap

			addToHashMapList(issuesByYearAndMonth, yearToMonth, issue);
		}

		// Reverse data so it is going newest-to-oldest

		for (String key : issuesByYearAndMonth.keySet()) {
			Collections.reverse(issuesByYearAndMonth.get(key));
		}

		String[] yearsArray = (String[])yearsList.toArray(
			new String[yearsList.size()]);

		String[] trueMonths = new String[12];

		for (int i = 11; i > 0; i--) {
			trueMonths[11 - i] = Month.of(
				i).getDisplayName(TextStyle.FULL, Locale.US);
		}

		renderRequest.setAttribute("articlesByIssue", articlesByIssue);
		renderRequest.setAttribute(
			"issuesByYearAndMonth", issuesByYearAndMonth);
		renderRequest.setAttribute("monthsByYear", monthsByYear);
		renderRequest.setAttribute("trueMonths", trueMonths);
		renderRequest.setAttribute("years", yearsArray);
		super.render(renderRequest, renderResponse);
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		NewsletterPortlet.class);

}