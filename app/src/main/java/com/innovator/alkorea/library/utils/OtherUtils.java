package com.innovator.alkorea.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


//Create innovator(JongChan Yang)
public class OtherUtils {

  private static final String preferenceKey = "preference";

  public static float convertDptoPx(Context context, float dp) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
  }

  public static String convertDateFormat(long dateInMilliseconds) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss.SSS");
    return simpleDateFormat.format(new Timestamp(dateInMilliseconds));
  }

  // 값 불러오기
  public static SharedPreferences getPreferences(Context context){
    return context.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
  }

  public static String getSharedPreferencesStringData(Context context, String key, String defaultValue) {
    return getPreferences(context).getString(key, defaultValue);
  }

  public static int getSharedPreferencesIntData(Context context, String key, int defaultValue) {
    return getPreferences(context).getInt(key, defaultValue);
  }

  public static void saveSharedPreferenceStringData(Context context, String key, String value) {
    SharedPreferences pref = getPreferences(context);
    SharedPreferences.Editor editor = pref.edit();
    editor.putString(key, value);
    editor.commit();
  }

  public static void saveSharedPreferenceIntData(Context context, String key, int value) {
    SharedPreferences pref = getPreferences(context);
    SharedPreferences.Editor editor = pref.edit();
    editor.putInt(key, value);
    editor.commit();
  }

  public static void removeSharedPreferenceData(Context context, String key) {
    SharedPreferences pref = getPreferences(context);
    SharedPreferences.Editor editor = pref.edit();
    editor.remove(key);
    editor.commit();
  }

  public static void removeAllSharedPreferenceData(Context context) {
    SharedPreferences pref = getPreferences(context);
    SharedPreferences.Editor editor = pref.edit();
    editor.clear();
    editor.commit();
  }

}
