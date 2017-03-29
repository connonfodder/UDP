package com.aadhk.kds.util;

import android.content.Context;

import com.aadhk.product.library.R;

public class LocaleUtil {
	public static final int EN_US = 0;
	public static final int ZH_TW = 1;
	public static final int ES_ES = 2;
	public static final int IT_IT = 3;
	public static final int DE_DE = 4;
	public static final int EL_GR = 5;
	public static final int FR_FR = 7;
	public static final int ZH_CN = 6;
	public static final int PT_BR = 8;
	public static final int NL_NL = 9;
	public static final int HU_HU = 10;
	public static final int IN_IN = 11;
	public static final int RU_RU = 12;
	public static final int DA_DA = 13;
	public static final int FI_FI = 14;
	public static final int SV_SV = 15;
	public static final int NB_NB = 16;
	public static final int JA_JA = 17;
	public static final int KO_KO = 18;
	public static final int TH_TH = 19;
	public static final int TR_TR = 20;
	public static final int CS_CZ = 21;

	private static final String CURR_USD = "USD";
	private static final String CURR_TWD = "TWD";
	private static final String CURR_DKK = "DKK";
	private static final String CURR_EUR = "EUR";
	private static final String CURR_HKD = "HKD";

	public static int getLangValue(String lang) {
		if (lang.equals("zh_TW") || lang.contains("zh_HK")) {
			return ZH_TW;
		} else if (lang.equals("es_ES") || lang.contains("es_")) {
			return ES_ES;
		} else if (lang.equals("it_IT") || lang.contains("it_")) {
			return IT_IT;
		} else if (lang.equals("de_DE") || lang.contains("de_")) {
			return DE_DE;
		} else if (lang.equals("el_GR") || lang.contains("el_")) {
			return EL_GR;
		} else if (lang.equals("nl_NL") || lang.contains("nl_")) {
			return NL_NL;
		} else if (lang.equals("hu_HU") || lang.contains("hu_")) {
			return HU_HU;
		} else if (lang.equals("fr_FR") || lang.contains("fr_")) {
			return FR_FR;
		} else if (lang.equals("pt_BR")) {
			return PT_BR;
		} else if (lang.equals("zh_CN")) {
			return ZH_CN;
		} else if (lang.equals("in_IN") || lang.contains("in_")) {
			return IN_IN;
		} else if (lang.equals("ru_RU") || lang.contains("ru_")) {
			return RU_RU;
		} else if (lang.equals("da_DA") || lang.contains("da_")) {
			return DA_DA;
		} else if (lang.equals("fi_FI") || lang.contains("fi_")) {
			return FI_FI;
		} else if (lang.equals("sv_SV") || lang.contains("sv_")) {
			return SV_SV;
		} else if (lang.equals("nb_NB") || lang.contains("nb_")) {
			return NB_NB;
		} else if (lang.equals("ja_JA") || lang.contains("ja_")) {
			return JA_JA;
		} else if (lang.equals("ko_KO") || lang.contains("ko_")) {
			return KO_KO;
		} else if (lang.equals("th_TH") || lang.contains("th_")) {
			return TH_TH;
		} else if (lang.equals("tr_TR") || lang.contains("tr_")) {
			return TR_TR;
		} else if (lang.equals("cs_CZ") || lang.contains("cs_")) {
			return CS_CZ;
		}/*  else if (lang.equals("da_DA") || lang.contains("da_")) {
			return DA_DA;
			} */else {
			return EN_US;
		}
		/*else if(lang.equals("ja_JP")||lang.contains("ja_")){
		return JA_JP;
		}else if(lang.equals("es_ES")||lang.contains("es_")){
		return ES_ES;*/

	}

	public static String getLang(int value) {
		if (value == ZH_TW) {
			return "zh_TW";
		} else if (value == ES_ES) {
			return "es_ES";
		} else if (value == IT_IT) {
			return "it_IT";
		} else if (value == DE_DE) {
			return "de_DE";
		} else if (value == EL_GR) {
			return "el_GR";
		} else if (value == HU_HU) {
			return "hu_HU";
		} else if (value == FR_FR) {
			return "fr_FR";
		} else if (value == NL_NL) {
			return "nl_NL";
		} else if (value == ZH_CN) {
			return "zh_CN";
		} else if (value == PT_BR) {
			return "pt_BR";
		} else if (value == IN_IN) {
			return "in_IN";
		} else if (value == RU_RU) {
			return "ru_RU";
		} else if (value == DA_DA) {
			return "da_DA";
		} else if (value == FI_FI) {
			return "fi_FI";
		} else if (value == SV_SV) {
			return "sv_SV";
		} else if (value == NB_NB) {
			return "nb_NB";
		} else if (value == JA_JA) {
			return "ja_JA";
		} else if (value == KO_KO) {
			return "ko_KO";
		} else if (value == TH_TH) {
			return "th_TH";
		} else if (value == TR_TR) {
			return "tr_TR";
		} else if (value == CS_CZ) {
			return "cs_CZ";
		}/* else if (value == DA_DA) {
			return "da_DA";
			}*/else {
			return "en_US";
		}
		/*
		}else if(value==JA_JP){
			return "ja_JP";
		}else if(value==ES_ES){
			return "es_ES";*/
	}

	public static String getLangName(Context context, int value) {
		if (value == ZH_TW) {
			return context.getString(R.string.tw);
		} else if (value == ES_ES) {
			return context.getString(R.string.es);
		} else if (value == IT_IT) {
			return context.getString(R.string.it);
		} else if (value == DE_DE) {
			return context.getString(R.string.de);
		} else if (value == EL_GR) {
			return context.getString(R.string.el);
		} else if (value == HU_HU) {
			return context.getString(R.string.hu);
		} else if (value == FR_FR) {
			return context.getString(R.string.fr);
		} else if (value == NL_NL) {
			return context.getString(R.string.nl);
		} else if (value == ZH_CN) {
			return context.getString(R.string.cn);
		} else if (value == PT_BR) {
			return context.getString(R.string.pt_BR);
		} else if (value == IN_IN) {
			return context.getString(R.string.in);
		} else if (value == RU_RU) {
			return context.getString(R.string.ru);
		} else if (value == DA_DA) {
			return context.getString(R.string.da);
		} else if (value == FI_FI) {
			return context.getString(R.string.fi);
		} else if (value == SV_SV) {
			return context.getString(R.string.sv);
		} else if (value == NB_NB) {
			return context.getString(R.string.nb);
		} else if (value == JA_JA) {
			return context.getString(R.string.ja);
		} else if (value == KO_KO) {
			return context.getString(R.string.ko);
		} else if (value == TH_TH) {
			return context.getString(R.string.th);
		} else if (value == TR_TR) {
			return context.getString(R.string.tr);
		} else if (value == CS_CZ) {
			return context.getString(R.string.cs);
		} else {
			return context.getString(R.string.en);
		}
	}

}
