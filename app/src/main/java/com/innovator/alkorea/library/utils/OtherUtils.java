package com.innovator.alkorea.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


//Create innovator(JongChan Yang)
public class OtherUtils {

  private static final String preferenceKey = "preference";

  public static float convertDpToPx(Context context, float dp) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
  }

  public static String convertDateFormat(long dateInMilliseconds) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss.SSS");
    return simpleDateFormat.format(new Timestamp(dateInMilliseconds));
  }

  // 값 불러오기
  public static SharedPreferences getPreferences(Context context) {
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

  public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

    List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

    // Sorting the list based on values
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        if (order) {
          return o1.getValue().compareTo(o2.getValue());
        } else {
          return o2.getValue().compareTo(o1.getValue());
        }
      }
    });

    // Maintaining insertion order with the help of LinkedList
    Map<String, Integer> sortedMap = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

  public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
    int childCount = viewGroup.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View view = viewGroup.getChildAt(i);
      view.setEnabled(enabled);
      if (view instanceof ViewGroup) {
        enableDisableViewGroup((ViewGroup) view, enabled);
      }
    }
  }


}
