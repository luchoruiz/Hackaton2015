package com.android.desafioaudionews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class PreferencesManager {
	private Context context;
	private static final String KEY = "audionews";
	private static final String KEY_LAST_UPDATE= "last_update";

	private static volatile PreferencesManager instance = null;

	private PreferencesManager(Context context) {
		this.context = context;
	}

	public static PreferencesManager getInstance(Context context) {
		if (instance == null) {
			synchronized (PreferencesManager.class) {
				if (instance == null) {
					instance = new PreferencesManager(context);
				}
			}
		}
		return instance;
	}

	public SharedPreferences getSharedPreferences() {
		return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
	}

	public void setListener(OnSharedPreferenceChangeListener listener) {
		getSharedPreferences().registerOnSharedPreferenceChangeListener(
				listener);
	}

	public String getLastUpdate() {
		return getSharedPreferences().getString(KEY_LAST_UPDATE, "");
	}

	/*public void saveSession(User user) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(LOGGED_ID, user.id);
		editor.putString(KEY_USER_ID, user.id);
		editor.putString(KEY_USERNAME, user.username);
		editor.putString(KEY_AVATAR, user.avatar);
		editor.putString(KEY_FULLNAME, user.fullname);
		editor.putBoolean(SESSION_OPENED, true);
		editor.commit();
	}*/



}