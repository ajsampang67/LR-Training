package com.liferay.training.amf.registration.validator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Maybe should've used SessionMessages/SessionErrors here
public class RegistrationValidator {

	public static boolean acceptedTOU(
		final boolean tou, final List<String> errors) {

		boolean result = true;

		if (!tou) {
			errors.add("Terms of Use not accepted");
			result = false;
		}

		return result;
	}

	public static boolean isValidAddress(
		final String address1, final String address2, final String city,
		final long state, final String zip, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(address1)) {
			errors.add("Please enter an address (starting on line 1)");
			result = false;
		} else if(address1.length() > 255 || address2.length() > 255) {
			errors.add("Address max length 255 characters per line");
			result = false;
		} else if(!Validator.isAlphanumericName(address1)) {
			errors.add("Invalid characters in address line 1");
			result = false;
		}

		if (Validator.isNotNull(address2)) {
			if (!Validator.isAlphanumericName(address2)) {
				errors.add("Invalid characters in address line 2");
				result = false;
			}
		}

		if (Validator.isNull(city)) {
			errors.add("Please enter a city");
			result = false;
		} else if(!Validator.isAlphanumericName(city)) {
			errors.add("Invalid characters in city");
			result = false;
		} else if(city.length() > 255) {
			errors.add("City max length 255 characters");
			result = false;
		}

		if (Validator.isNull(state)) {
			errors.add("Please choose a state");
			result = false;
		} else {
			try {
				RegionServiceUtil.getRegion(state);
			} catch (PortalException e) {
				errors.add("Please choose a state with a valid Region Id");
				result = false;
			}
		}

		if (Validator.isNull(zip)) {
			errors.add("Please enter a zip code");
			result = false;
		} else if(!Validator.isNumber(zip)) {
			errors.add("Invalid characters in zip code");
			result = false;
		} else if(zip.length() != 5) {
			errors.add("Please enter a 5 digit zip code");
			result = false;
		}

		return result;
	}

	public static boolean isValidBirthday(
		final int b_month, final int b_day, final int b_year,
		final List<String> errors) {

		boolean result = true;

		if (!Validator.isDate(b_month, b_day, b_year)) {
			errors.add("Birthday invalid");
			result = false;
		}

		Date bday = CalendarFactoryUtil.getCalendar(
			b_year, b_month, b_day).getTime();

		int age = CalendarUtil.getAge(bday, Calendar.getInstance());

		if (age < 13) {
			errors.add("Must be 13 years of age or older to register");
			result = false;
		}

		return result;
	}

	public static boolean isValidEmail(
		final String email, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(email)) {
			errors.add("Please enter an email");
			result = false;
		} else if(email.length() > 255) {
			errors.add("Max length email 255 characters");
			result = false;
		} else if(!Validator.isEmailAddress(email)) {
			errors.add("Please enter a valid email address");
			result = false;
		}

		return result;
	}

	public static boolean isValidFName(
		final String fname, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(fname)) {
			errors.add("Please add a First Name");
			result = false;
		} else if(fname.length() > 50) {
			errors.add("Max length first name 50 characters");
			result = false;
		} else if(!Validator.isAlphanumericName(fname)) {
			errors.add("First name must be alphanumeric");
			result = false;
		}

		return result;
	}

	public static boolean isValidLName(
		final String lname, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(lname)) {
			errors.add("Please add a Last Name");
			result = false;
		} else if (lname.length() > 50) {
			errors.add("Max length last name 50 characters");
			result = false;
		} else if (!Validator.isAlphanumericName(lname)) {
			errors.add("Last name must be alphanumeric");
			result = false;
		}

		return result;
	}

	public static boolean isValidPassword(
		final String p1, final String p2, final List<String> errors) {

		boolean result = true;

		boolean minLength = p1.length()>=6;
		boolean minOneUp = !p1.equals(p1.toLowerCase());
		boolean minOneNumber = p1.matches(".*[0-9].*");
		boolean minOneSpecial = !p1.matches("[A-Za-z0-9]*");

		if (!minLength) {
			errors.add("Password must be at least 6 characters long");
			result = false;
		}

		if (!minOneUp) {
			errors.add("Password must have at least one uppercase letter");
			result = false;
		}

		if (!minOneNumber) {
			errors.add("Password must have at least one number");
			result = false;
		}

		if (!minOneSpecial) {
			errors.add("Password must have at least one special character");
			result = false;
		}

		if (!(p1.equals(p2))) {
			errors.add("Passwords do not match");
			result = false;
		}

		return result;
	}

	public static boolean isValidPhone(
		final String phoneNumber, final List<String> errors) {

		boolean result = true;

		if (!Validator.isPhoneNumber(phoneNumber)) {
			errors.add("Please enter a valid phone number");
		}

		if (phoneNumber.length() != 10) {
			errors.add("Please enter a 10 digit phone number.");
			result = false;
		}

		return result;
	}

	public static boolean isValidSecurityQ(
		final String question, final String answer, final List<String> errors) {

		boolean result = true;

		List<String> validQs = Arrays.asList(
						"What is your mother's maiden name?",
						"What is the make of your first car?",
						"What is your high school mascot?",
						"Who is your favorite actor?");

		if (Validator.isNull(question)) {
			errors.add("Please select a security question and answer");
			result = false;
		} else if(!validQs.contains(question)) {
			errors.add("Please select a security question from the list");
			result = false;
		}

		if (Validator.isNull(answer)) {
			errors.add("Please answer security question");
			result = false;
		} else if(answer.length() > 255) {
			errors.add("Answer max length 255 characters");
			result = false;
		}

		return result;
	}

	public static boolean isValidUsername(
		final String uname, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(uname)) {
			errors.add("Please enter a username");
			result = false;
		} else if(uname.length() < 4 || uname.length() > 16) {
			errors.add("Username must be between 4 and 16 characters");
			result = false;
		} else if(!Validator.isAlphanumericName(uname)) {
			errors.add("Username must be alphanumeric");
			result = false;
		}

		return result;
	}

}