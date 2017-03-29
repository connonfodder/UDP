package com.aadhk.product.util;

public class DataTypeUtil {

	public static int toInt(boolean value) {
		return value ? 1 : 0;
	}

	public static boolean toBoolean(int value) {
		return (value != 0);
	}

	public static int findPeriodPosition(String[] periodValues, String value) {
		int index = 0;
		for (int i = 0; i < periodValues.length; i++) {
			if (periodValues[i].equals(value)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public static int findPeriodPosition(int[] periodValues, int value) {
		int index = 0;
		for (int i = 0; i < periodValues.length; i++) {
			if (periodValues[i]==value) {
				index = i;
				break;
			}
		}
		return index;
	}

}
