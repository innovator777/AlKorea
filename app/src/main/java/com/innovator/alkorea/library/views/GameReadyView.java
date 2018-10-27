package com.innovator.alkorea.library.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovator.alkorea.library.utils.OtherUtils;

//Create innovator(JongChan Yang)
public class GameReadyView extends RelativeLayout {

  private final String TAG = GameReadyView.class.getName();

  private Context context;

  private String countArray[] = {"3", "2", "1", "GO!"};
  private RelativeLayout backgroundLayout;
  private TextView titleTextView;
  private TextView numberCountTextView;
  private TextView explanationTextView;

  private int countIndex = 0;

  public GameReadyView(Context context) {
    super(context);
    init(context);
  }

  public GameReadyView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public GameReadyView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    this.context = context;
    setUI();
  }

  private void setUI(){
    RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    setLayoutParams(rootLayoutParams);

    RelativeLayout.LayoutParams backgroundParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);

    backgroundLayout = new RelativeLayout(context);
    backgroundLayout.setLayoutParams(backgroundParams);
    backgroundLayout.setBackgroundColor(Color.BLACK);
    backgroundLayout.setAlpha(0.5f);

    RelativeLayout.LayoutParams titleTextViewParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    titleTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    titleTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    titleTextViewParams.topMargin = 100;

    titleTextView = new TextView(context);
    titleTextView.setLayoutParams(titleTextViewParams);
    titleTextView.setTypeface(null, Typeface.BOLD);
    titleTextView.setTextSize(OtherUtils.convertDptoPx(context,24));

    RelativeLayout.LayoutParams numberCountTextViewParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    numberCountTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    numberCountTextViewParams.addRule(RelativeLayout.CENTER_VERTICAL);

    numberCountTextView = new TextView(context);
    numberCountTextView.setLayoutParams(numberCountTextViewParams);
    numberCountTextView.setTypeface(null, Typeface.BOLD);
    numberCountTextView.setTextSize(OtherUtils.convertDptoPx(context, 72));

    RelativeLayout.LayoutParams explanationTextViewParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    explanationTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    explanationTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    explanationTextViewParams.bottomMargin = 100;

    explanationTextView = new TextView(context);
    explanationTextView.setLayoutParams(explanationTextViewParams);
    explanationTextView.setTypeface(null, Typeface.BOLD);
    explanationTextView.setTextSize(OtherUtils.convertDptoPx(context,12));

    backgroundLayout.addView(titleTextView);
    backgroundLayout.addView(numberCountTextView);
    backgroundLayout.addView(explanationTextView);
    addView(backgroundLayout);
  }

  public String[] getCountArray() {
    return countArray;
  }

  public void upCountIndex() {
    if (countIndex < countArray.length - 1)
      countIndex++;
  }

  public int getCountIndex() {
    return countIndex;
  }

  public TextView getNumberCountTextView() {
    return numberCountTextView;
  }

  public TextView getTitleTextView() {
    return titleTextView;
  }

  public TextView getExplanationTextView() {
    return explanationTextView;
  }
}
