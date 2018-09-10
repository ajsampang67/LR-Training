package com.liferay.training.amf.registration.service.validator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RegistrationValidator {

	public static boolean acceptedTOU(
		final boolean tou, final List<String> errors) {

		boolean result = true;

		if (!tou) {
			errors.add("please-accept-terms-of-use");
			result = false;
		}

		return result;
	}

	public static boolean isValidAddress(
		final String address1, final String address2, final String city,
		final long state, final String zip, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(address1)) {
			errors.add("please-enter-an-address-starting-on-line-1");
			result = false;
		}
		else if ((address1.length() > 255) || (address2.length() > 255)) {
			errors.add("address-max-length-255-characters-per-line");
			result = false;
		}
		else if (!Validator.isAlphanumericName(address1)) {
			errors.add("invalid-characters-in-address-line-1");
			result = false;
		}

		if (Validator.isNotNull(address2)) {
			if (!Validator.isAlphanumericName(address2)) {
				errors.add("invalid-characters-in-address-line-2");
				result = false;
			}
		}

		if (Validator.isNull(city)) {
			errors.add("please-enter-a-city");
			result = false;
		}
		else if (!Validator.isAlphanumericName(city)) {
			errors.add("invalid-characters-in-city");
			result = false;
		}
		else if (city.length() > 255) {
			errors.add("city-max-length-255-characters");
			result = false;
		}

		if (Validator.isNull(state)) {
			errors.add("please-choose-a-state");
			result = false;
		}
		else {
			try {
				RegionServiceUtil.getRegion(state);
			}
			catch (PortalException pe) {
				errors.add("please-choose-a-state-with-a-valid-region-id");
				result = false;
			}
		}

		if (Validator.isNull(zip)) {
			errors.add("please-enter-a-zip-code");
			result = false;
		}
		else if (!Validator.isNumber(zip)) {
			errors.add("invalid-characters-in-zip-code");
			result = false;
		}
		else if (zip.length() != 5) {
			errors.add("please-enter-a-5-digit-zip-code");
			result = false;
		}

		return result;
	}

	public static boolean isValidBirthday(
		final int birthMonth, final int birthDay, final int birthYear,
		final List<String> errors) {

		boolean result = true;

		if (!Validator.isDate(birthMonth, birthDay, birthYear)) {
			errors.add("birthday-is-not-a-valid-date");
			result = false;
		}

		Date bday = CalendarFactoryUtil.getCalendar(
			birthYear, birthMonth, birthDay).getTime();

		int age = CalendarUtil.getAge(bday, Calendar.getInstance());

		if (age < 13) {
			errors.add("must-be-13-years-of-age-or-older-to-register");
			result = false;
		}

		return result;
	}

	public static boolean isValidEmail(
		final String email, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(email)) {
			errors.add("please-enter-an-email");
			result = false;
		}
		else if (email.length() > 255) {
			errors.add("email-max-length-255-characters");
			result = false;
		}
		else if (!Validator.isEmailAddress(email)) {
			errors.add("please-enter-a-valid-email-address");
			result = false;
		}

		return result;
	}

	public static boolean isValidFName(
		final String fname, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(fname)) {
			errors.add("please-add-a-first-name");
			result = false;
		}
		else if (fname.length() > 50) {
			errors.add("name-max-length-50-characters-per-line");
			result = false;
		}
		else if (!Validator.isAlphanumericName(fname)) {
			errors.add("name-must-be-alphanumeric");
			result = false;
		}

		return result;
	}

	public static void isValidForm(
		List<String> errors, String firstName, String lastName,
		String emailAddress, String userName, int birthMonth, int birthDay,
		int birthYear, String password1, String password2, String street1,
		String street2, String city, long regionId, String zip, String secQ,
		String secA, boolean tou) {

		isValidFName(firstName, errors);
		isValidLName(lastName, errors);
		isValidEmail(emailAddress, errors);
		isValidUsername(userName, errors);
		isValidBirthday(birthMonth, birthDay, birthYear, errors);
		isValidPassword(password1, password2, errors);
		isValidAddress(street1, street2, city, regionId, zip, errors);
		isValidSecurityQ(secQ, secA, errors);
		acceptedTOU(tou, errors);
	}

	public static boolean isValidLName(
		final String lname, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(lname)) {
			errors.add("please-add-a-last-name");
			result = false;
		}
		else if (lname.length() > 50) {
			errors.add("name-max-length-50-characters-per-line");
			result = false;
		}
		else if (!Validator.isAlphanumericName(lname)) {
			errors.add("name-must-be-alphanumeric");
			result = false;
		}

		return result;
	}

	public static boolean isValidPassword(
		final String p1, final String p2, final List<String> errors) {

		if (Validator.isNull(p1) || Validator.isNull(p2)) {
			errors.add("please-enter-a-password-and-repeat");
		}

		boolean result = true;

		boolean minLength = p1.length()>=6;
		boolean minOneUp = !p1.equals(p1.toLowerCase());
		boolean minOneNumber = p1.matches(".*[0-9].*");
		boolean minOneSpecial = !p1.matches("[A-Za-z0-9]*");

		if (!minLength) {
			errors.add("password-must-be-at-least-6-characters-long");
			result = false;
		}

		if (!minOneUp) {
			errors.add("password-must-have-at-least-one-uppercase-letter");
			result = false;
		}

		if (!minOneNumber) {
			errors.add("password-must-have-at-least-one-number");
			result = false;
		}

		if (!minOneSpecial) {
			errors.add("password-must-have-at-least-one-special-character");
			result = false;
		}

		if (!(p1.equals(p2))) {
			errors.add("passwords-do-not-match");
			result = false;
		}

		return result;
	}

	public static boolean isValidPhone(
		final String phoneNumber, final List<String> errors) {

		boolean result = true;

		if (!Validator.isPhoneNumber(phoneNumber)) {
			errors.add("please-enter-a-valid-phone-number");
			result = false;
		}

		if (phoneNumber.length() != 10) {
			errors.add("please-enter-a-10-digit-phone-number");
			result = false;
		}

		return result;
	}

	public static boolean isValidSecurityQ(
		final String question, final String answer, final List<String> errors) {

		boolean result = true;

		List<String> validQs = Arrays.asList(
			"what-is-your-mothers-maiden-name?",
			"what-is-the-make-of-your-first-car?",
			"what-is-your-high-school-mascot?", "who-is-your-favorite-actor?");

		if (Validator.isNull(question)) {
			errors.add("please-select-a-security-question-and-answer");
			result = false;
		}

		if (Validator.isNull(answer)) {
			errors.add("please-answer-security-question");
			result = false;
		}
		else if (answer.length() > 255) {
			errors.add("answer-max-length-255-characters");
			result = false;
		}

		return result;
	}

	public static boolean isValidUsername(
		final String uname, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(uname)) {
			errors.add("please-enter-a-username");
			result = false;
		}
		else if (uname.length() < 4 || uname.length() > 16) {
			errors.add("username-must-be-between-4-and-16-characters");
			result = false;
		}
		else if (!Validator.isAlphanumericName(uname)) {
			errors.add("username-must-be-alphanumeric");
			result = false;
		}

		return result;
	}

}