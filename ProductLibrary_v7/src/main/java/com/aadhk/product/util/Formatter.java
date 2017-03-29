package com.aadhk.product.util;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.acra.ACRA;

public class Formatter {

    public static String displayAmount(double value, String currSign) {
        String pattern = "'" + currSign.replace("'", "''") + "' #,###,##0.00";
        DecimalFormat df = new DecimalFormat(pattern);
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }

    public static String displayAmount(double value) {
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }

    public static String displayPrice(double value, String currSign) {
        String pattern = "'" + currSign.replace("'", "''") + "' #,###,##0.#";
        DecimalFormat df = new DecimalFormat(pattern);
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }

    public static String displayPrice(double value) {
        DecimalFormat df = new DecimalFormat("#,###,##0.#");
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }

    public static String displayQty(double value) {
        DecimalFormat df = new DecimalFormat("##0.#");
        return df.format(value);
    }

    public static String displayQty(double value, int maxFractionDigit) {
        DecimalFormat df = new DecimalFormat("##0.#");
        df.setMaximumFractionDigits(maxFractionDigit);
        return df.format(value);
    }

    public static String displayUsWithoutZero(double value, int digits) {
        String result = "";
        if (value != 0) {
            return displayUsWithZero(value, digits);
        }
        return result;
    }

    public static String displayUsWithoutZero(double value) {
        return displayUsWithoutZero(value, 2);
    }


    public static String displayUsWithZero(double value) {
        return displayUsWithZero(value, 2);
    }

    public static String displayUsWithZero(double value, int digits) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyPattern("##0.#");
        df.setMaximumFractionDigits(digits);
        return df.format(value);
    }

    public static String displayPercentage(double value) {
        String result = "";
        if (value != 0) {
            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("###");
            result = df.format(value);
        }
        return result;
    }

    public static String displayPercentageTwoDecimals(double value) {
        String result = "";
        if (value != 0) {
            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("##0.00");
            result = df.format(value);
        }
        return result;
    }

    public static String exportAmt(double value) {
        DecimalFormat df = new DecimalFormat("##0.#");
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }

    public static String getCurrencySign(String value) {
        int startIndex = value.indexOf("(");
        int endIndex = value.indexOf(")");
        return value.substring(startIndex + 1, endIndex);
    }

    public static String getShortCurrency(String value) {
        int endIndex = value.indexOf("(");
        return value.substring(0, endIndex);
    }

    public static String googleChartFormatAmt(double value) {
        String pattern = "##0.#";
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyPattern(pattern);
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }

    public static String googleChartFormatPecent(double value) {
        DecimalFormat df = new DecimalFormat("##0.#");
        df.setMaximumFractionDigits(1);
        return df.format(value);
    }

    public static List<Long> convertArrayList(long[] value) {
        List<Long> result = new ArrayList<Long>();
        for (int i = 0; i < value.length; i++) {
            result.add(value[i]);
        }
        return result;
    }

    public static long[] convertArray(List<Long> value) {
        long[] result = new long[value.size()];
        for (int i = 0; i < value.size(); i++) {
            result[i] = value.get(i);
        }
        return result;
    }

    public static String nullToString(String value) {
        return value == null ? "" : value;
    }

    public static String intWithoutZero(int value) {
        String result = "";
        if (value != 0) {
            result = value + "";
        }
        return result;
    }

    public static double parseDouble(String value) {
        double result = 0;
        try {
            if (value != null && !value.equals("") && !value.equals(".")) {
                DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
                Number number = df.parse(value);
                result = number.doubleValue();
            }
        } catch (ParseException e) {
            ACRA.getErrorReporter().handleException(e);
            e.printStackTrace();
        }
        return result;
    }

    /*
     * The precision of a float is about 6 or 7 decimal digits, so if you try to store numbers with more digits (such as 666666.77 which is 8 digits) then you'll see rounding errors.
     */
    public static float parseFloat(String value) {
        float result = 0;
        try {
            if (value != null && !value.equals("") && !value.equals(".")) {
                DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
                Number number = df.parse(value);
                result = number.floatValue();
            }
        } catch (ParseException e) {
            ACRA.getErrorReporter().handleException(e);
            e.printStackTrace();
        }
        return result;
    }

    public static long parseLong(String value) {
        long result = 0;
        try {
            if (value != null && !value.equals("")) {
                DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
                Number number = df.parse(value);
                result = number.longValue();
            }
        } catch (ParseException e) {
            ACRA.getErrorReporter().handleException(e);
            e.printStackTrace();
        }
        return result;
    }

    public static int parseInt(String value) {
        if (value != null && !value.equals(""))
            return Integer.parseInt(value);
        return 0;
    }

    public static boolean parseBoolean(String value) {
        if (value != null && !value.equals(""))
            return Boolean.parseBoolean(value);
        return false;
    }

    public static boolean toBoolean(int value) {
        return (value != 0);
    }

    public static char toChar(boolean value) {
        return value ? 'Y' : 'N';
    }

    public static int toInt(boolean value) {
        return value ? 1 : 0;
    }

    public static int toInt(String value) {
        int result = 0;
        if (value != null && !"".equals(value)) {
            result = Integer.parseInt(value);
        }
        return result;
    }

    public static long toLong(String value) {
        long result = 0;
        if (value != null && !"".equals(value)) {
            result = Long.parseLong(value);
        }
        return result;
    }

    public static short toShort(boolean value) {
        return (short) (value ? 1 : 0);
    }

    public static String toString(CharSequence[] chars) {
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            result += chars[i];
            if (i != chars.length - 1) {
                result += ", ";
            }
        }
        return result;
    }

    public static String toString(Set<String> chars) {
        String result = "";
        int index = 1;
        for (String str : chars) {
            result += str;
            if (index < chars.size()) {
                result += ", ";
            }
            index++;
        }
        return result;
    }

    public static boolean isEmpty(String value) {
        boolean result = false;
        if (value == null || "".equals(value)) {
            result = true;
        }
        return result;
    }

    public static int getPosition(int[] values, int value) {
        int position = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                position = i;
                break;
            }
        }
        return position;
    }
}
