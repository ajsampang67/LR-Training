package com.liferay.amf.newsletter.portlet;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.StringReader;

import java.time.Month;
import java.time.format.TextStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author Alfred Sampang
 */
public class AmfNewsletterHelpers {

	// Hacky code for parsing XML found at
	// https://community.liferay.com/es/forums/-/message_boards/message/20924860

	public static <T> void addToHashMapList(
		HashMap<T, List> listByKey, T key, Object value) {

		if (listByKey.containsKey(key)) {
			List curList = listByKey.get(key);

			if (!curList.contains(value)) {
				curList.add(value);
			}
		}
		else {
			List valueList = new ArrayList();

			valueList.add(value);
			listByKey.put(key, valueList);
		}
	}

	public static String getArticleFieldValue(JournalArticle article, String
			fieldName) {

		String fieldValue = "";

		try {
			String content = article.getContent();
			Document document = null;

			try {
				document = SAXReaderUtil.read(new StringReader(content));
			}
			catch (Exception de) {
				de.printStackTrace();
			}

			if (Validator.isNotNull(document)) {
				Node fieldContent = document.selectSingleNode(
					"//*/dynamic-element[@name='" + fieldName +
						"']/dynamic-content");

				if (fieldContent != null) {
					fieldValue = fieldContent.getText();
				}
			}
		}
		catch (SystemException se) {
			se.printStackTrace();
		}

		return fieldValue;
	}

	public static String getMonthDisplayName(String issueDate) {
		String issueMonthStr = issueDate.substring(5, 7);

		int issueMonthInt = Integer.parseInt(issueMonthStr);

		String issueMonth = Month.of(
			issueMonthInt).getDisplayName(TextStyle.FULL, Locale.US);

		return issueMonth;
	}

}