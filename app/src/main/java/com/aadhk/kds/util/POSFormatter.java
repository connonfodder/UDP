package com.aadhk.kds.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aadhk.product.util.Formatter;

public class POSFormatter extends Formatter {

	public static boolean validateOrderNo(String value) {
		Pattern p = Pattern.compile("[a-zA-Z0-9-]*[0-9]+");
		Matcher m = p.matcher(value);
		return m.matches();
	}

	public static String increaseOrderNo(String value) {
		if (value != null && !value.equals("")) {
			Pattern digitPattern = Pattern.compile("([0-9]+)"); // EDIT: Increment digits.
			Matcher matcher = digitPattern.matcher(value);
			StringBuffer result = new StringBuffer();
			while (matcher.find()) {
				String found = matcher.group(1);
				if (matcher.hitEnd()) {
					String format = String.format("%%0%dd", found.length());
					matcher.appendReplacement(result, String.format(format, Long.parseLong(found) + 1));
				}
			}
			matcher.appendTail(result);
			return result.toString();
		}
		return "A-00001";
	}

	public static String displayAmount(int decimalPlace, double value, String currSign) {
		String pattern = "'" + currSign.replace("'", "''") + "'#,###,##0";
		if (decimalPlace == 0) {
			pattern += "";
		} else if (decimalPlace == 1) {
			pattern += ".0";
		} else {
			pattern += ".00";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(value);
	}

	public static String displayUsAmount(int decimalPlace, double value, String currSign) {
		String pattern = "'" + currSign.replace("'", "''") + "'#,###,##0";
		if (decimalPlace == 0) {
			pattern += "";
		} else if (decimalPlace == 1) {
			pattern += ".0";
		} else {
			pattern += ".00";
		}
		DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
		df.applyPattern(pattern);
		return df.format(value);
	}

	public static String displayAmount(int decimalPlace, double value) {
		String pattern = "";
		if (decimalPlace == 0) {
			pattern += "#,###,##0";
		} else if (decimalPlace == 1) {
			pattern += "#,###,##0.0";
		} else {
			pattern += "#,###,##0.00";
		}

		//Log.i("test", "pattern"+pattern);

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(value);
	}
}
