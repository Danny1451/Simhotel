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

    public static void setLastPwd(Context context,String pwd){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("lastpassword",pwd).commit();
    }

    public static String getLastPwd(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString("lastpassword","");
    }

    public static String getCharacter(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString("character","");
    }
    public static void setCharacter(Context context,String character){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("character",character).commit();
    }

    public static String getTeamNum(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString("teamnum","");
    }
    public static void setTeamNum(Context context,String teamNum){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("teamnum",teamNum).commit();
    }

    public static Boolean getIsTeacher(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getBoolean("isteacher",false);
    }

    public static void setIsTeacher(Context context, Boolean isTeacher){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean("isteacher",isTeacher).commit();
    }

}
