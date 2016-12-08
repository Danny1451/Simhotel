package com.real.simhotel.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by liudan on 2016/12/8.
 */
public class PreferenceUtils {

    public static void setLastUser(Context context,String username){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("lastusername",username).commit();
    }

    public static String getLastUser(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString("lastusername","");
    }
}
