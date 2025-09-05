package com.example.app_waitingticket.printer.fixedKichenPrint;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;

public final class PreferenceUtils {
    private PreferenceUtils(){}

    /**
     *
     * get All Preferences
     * @param context
     * @param name : Preferences File Name
     * @return
     */
    protected static Map<String,?> getAll(Context context, String name) {
        Map<String,?> value = null;

        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            value = sharedPreferences.getAll();
        }

        return value;
    }

    /**
     * get String Preferences
     * @param context
     * @param name : Preferences File Name
     * @param key : Preferences Key
     * @return : String Value
     */
    protected static String getString(Context context, String name, String key) {
        String value = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            value = sharedPreferences.getString(key,null);
        }

        return value;
    }

    /**
     * set String Preferences
     * @param context
     * @param name : Preferences File Name
     * @param key : intput Key
     * @param value : input Value
     * @return : true / false
     */
    protected static boolean putString(Context context, String name, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null && !TextUtils.isEmpty(key)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key,value);
            return editor.commit();
        }

        return false;
    }

    /**
     * set bool Preferences
     * @param context
     * @param name : Preferences File Name
     * @param key : intput Key
     * @param value : input Value
     * @return : true / false
     */
    protected static boolean putBoolean(Context context, String name, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null && !TextUtils.isEmpty(key)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key,value);
            return editor.commit();
        }

        return false;
    }

    /**
     * get Boolean Preferences
     * @param context
     * @param name : Preferences File Name
     * @param key : Preferences Key
     * @return : String Value
     */
    protected static Boolean getBoolean(Context context, String name, String key) {
        Boolean value = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            value = sharedPreferences.getBoolean(key,false);
        }

        return value;
    }


    /**
     * get Integer Preferences
     * @param context
     * @param name : Preferences File Name
     * @param key : Preferences Key
     * @Param defaultValue
     * @return : Integer Value
     */
    protected static int getInteger(Context context, String name, String key, int defaultValue){
        int value = defaultValue;
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            value = sharedPreferences.getInt(key,defaultValue);
        }
        return value;
    }

    /**
     * set Integer Preferences
     * @param context
     * @param name : Preferences File Name
     * @param key : input Key
     * @param value : input Key
     * @return : true / false
     */
    protected static boolean putInteger(Context context, String name, String key, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key,value);
            return editor.commit();
        }
        return false;
    }

    protected static boolean remove(Context context, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            return editor.commit();
        }
        return false;
    }


}
