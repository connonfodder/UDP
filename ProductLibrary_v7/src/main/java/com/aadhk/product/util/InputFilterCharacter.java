package com.aadhk.product.util;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterCharacter implements InputFilter {

	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		for (int i = start; i < end; i++) {
			char c = source.charAt(i);
			if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
				return "";
			}
		}
		return null;
	}

}
