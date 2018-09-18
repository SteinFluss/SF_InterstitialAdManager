package com.steinfluss.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

public class PreferenceUtils {
    //pref for ads
    private static final String KEY_PREF_ADS = "key_show_ads_in_appasdfasdfasdfasdgawe";

    private static SharedPreferences getSharedPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getEditor(Context context){
        return getSharedPref(context).edit();
    }

    public static boolean showAds(Context context){
        return getSharedPref(context).getBoolean(KEY_PREF_ADS,true);
    }

    public static void disableAds(Context context){
        getEditor(context)
                .putBoolean(KEY_PREF_ADS,false)
                .apply();
    }
}

