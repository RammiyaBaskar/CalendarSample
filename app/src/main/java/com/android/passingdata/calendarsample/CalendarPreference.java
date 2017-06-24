package com.android.passingdata.calendarsample;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 17-03-2017.
 */

public class CalendarPreference {
    public Context mContext;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private static String PREF_NAME = "CalendarPreference";
    private static String CLICKED_DATE_PREFERENCE  = "clicked_date_preference";

    public CalendarPreference(Context context){
        mContext = context;
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }



    public void setClickedDate(String value){
        mEditor.putString(CLICKED_DATE_PREFERENCE, value);
        mEditor.commit();
    }

    public String getClickedDate(){
        return mPrefs.getString(CLICKED_DATE_PREFERENCE, "");
    }

}
