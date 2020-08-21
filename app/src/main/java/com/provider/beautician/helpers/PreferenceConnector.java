package com.provider.beautician.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.provider.beautician.constant.Constant;

import java.util.Map;

public class PreferenceConnector {
	private static final String PREF_NAME 						= 	Constant.PACKAGE_NAME;
	private static final int 	MODE 							= 	Context.MODE_PRIVATE;

	public static final String KEY_IS_LOGIN        				=   "KEY_IS_LOGIN";
	public static final String KEY_USER_ID        				=   "KEY_USER_ID";
	public static final String KEY_SALOON_ID        			=   "KEY_SALOON_ID";
	public static final String KEY_STAFF_ID        				=   "KEY_STAFF_ID";
	public static final String KEY_USER_NAME        			=   "KEY_USER_NAME";
	public static final String KEY_USER_FIRST_NAME      		=   "KEY_USER_FIRST_NAME";
	public static final String KEY_USER_LAST_NAME      			=   "KEY_USER_LAST_NAME";
	public static final String KEY_USER_PROFILE_IMAGE      		=   "KEY_USER_PROFILE_IMAGE";
	public static final String KEY_USER_EMAIL         			=   "KEY_USER_EMAIL";
	public static final String KEY_USER_PHONE         			=   "KEY_USER_PHONE";
	public static final String KEY_USER_ADDRESS         		=   "KEY_USER_ADDRESS";
	public static final String KEY_IS_UPDATE_REQUIRED 			= 	"KEY_IS_UPDATE_REQUIRED";
	public static final String KEY_IS_FIRST_TIME	 			= 	"KEY_IS_FIRST_TIME";
	public static final String LANG_CODE    					=  	"LANG_CODE";

	//TIMER FOR UPDATE FCM TOKEN
	public static final String KEY_TIME 						= 	"KEY_TIME";
	public static final String DEVICE_TOKEN 					= 	"DEVICE_TOKEN";


	public static void writeBoolean(Context context, String key, boolean value) {
		getEditor(context).putBoolean(key, value).commit();
	}

	public static boolean readBoolean(Context context, String key, boolean defValue) {
		return getPreferences(context).getBoolean(key, defValue);
	}

	public static void writeInteger(Context context, String key, int value) {
		getEditor(context).putInt(key, value).commit();
	}

	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}


	public static void writeString(Context context, String key, String string) {
		getEditor(context).putString(key, string).commit();
	}

	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}

	public static void writeFloat(Context context, String key, float value) {
		getEditor(context).putFloat(key, value).commit();
	}

	public static float readFloat(Context context, String key, float defValue) {
		return getPreferences(context).getFloat(key, defValue);
	}

	public static void writeLong(Context context, String key, long value) {
		getEditor(context).putLong(key, value).commit();
	}

	public static long readLong(Context context, String key, long defValue) {
		return getPreferences(context).getLong(key, defValue);
	}

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	private static Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}

	public static void cleanPrefrences(Context context){
		getPreferences(context).edit().clear().apply();
	}

	public static Map<String, ?> getAllKeys(Context context) {
		return getPreferences(context).getAll();
	}
}
