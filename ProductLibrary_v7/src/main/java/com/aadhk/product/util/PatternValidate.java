package com.aadhk.product.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternValidate {

	public static final Pattern ESCPOS_COMM = Pattern.compile("^[0-9A-Fa-f,]*$");

	public static final Pattern IP_ADDRESS = Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]" + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]" + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}" + "|[1-9][0-9]|[0-9]))");

	public static final Pattern EMAIL_ADDRESS = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	/**
	 * This pattern is intended for searching for things that look like they
	 * might be phone numbers in arbitrary text, not for validating whether
	 * something is in fact a phone number.  It will miss many things that
	 * are legitimate phone numbers.
	 * 
	 * <p> The pattern matches the following:
	 * <ul>
	 * <li>Optionally, a + sign followed immediately by one or more digits. Spaces, dots, or dashes
	 * may follow.
	 * <li>Optionally, sets of digits in parentheses, separated by spaces, dots, or dashes.
	 * <li>A string starting and ending with a digit, containing digits, spaces, dots, and/or dashes.
	 * </ul>
	 */
	public static final Pattern PHONE = Pattern.compile( // sdd = space, dot, or dash
			"(\\+[0-9]+[\\- \\.]*)?" // +<digits><sdd>*
					+ "(\\([0-9]+\\)[\\- \\.]*)?" // (<digits>)<sdd>*
					+ "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])"); // <digit><digit|sdd>+<digit> 

	/**
	 *  Convenience method to take all of the non-null matching groups in a
	 *  regex Matcher and return them as a concatenated string.
	 *
	 *  @param matcher      The Matcher object from which grouped text will
	 *                      be extracted
	 *
	 *  @return             A String comprising all of the non-null matched
	 *                      groups concatenated together
	 */
	public static final String concatGroups(Matcher matcher) {
		StringBuilder b = new StringBuilder();
		final int numGroups = matcher.groupCount();

		for (int i = 1; i <= numGroups; i++) {
			String s = matcher.group(i);

			System.err.println("Group(" + i + ") : " + s);

			if (s != null) {
				b.append(s);
			}
		}

		return b.toString();
	}

	/**
	 * Convenience method to return only the digits and plus signs
	 * in the matching string.
	 *
	 * @param matcher      The Matcher object from which digits and plus will
	 *                     be extracted
	 *
	 * @return             A String comprising all of the digits and plus in
	 *                     the match
	 */
	public static final String digitsAndPlusOnly(Matcher matcher) {
		StringBuilder buffer = new StringBuilder();
		String matchingRegion = matcher.group();

		for (int i = 0, size = matchingRegion.length(); i < size; i++) {
			char character = matchingRegion.charAt(i);

			if (character == '+' || Character.isDigit(character)) {
				buffer.append(character);
			}
		}
		return buffer.toString();
	}

	/**
	 * Do not create this static utility class.
	 */
	private PatternValidate() {
	}

}
