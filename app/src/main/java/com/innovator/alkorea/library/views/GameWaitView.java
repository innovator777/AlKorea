package com.innovator.alkorea.library.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovator.alkorea.library.utils.OtherUtils;

public class GameWaitView extends RelativeLayout {

  private final String TAG = GameWaitView.class.getName();

  private Context context;

  private RelativeLayout backgroundLayout;
  private TextView contentsTextView;

  public GameWaitView(Context context) {
    super(context);
    init(context);
  }

  public GameWaitView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public GameWaitView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void init(Context context) {
    this.context = context;
    setUI();
  }

  private void setUI() {
    RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    setLayoutParams(rootLayoutParams);

    RelativeLayout.LayoutParams backgroundLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    backgroundLayout = new RelativeLayout(context);
    backgroundLayout.setLayoutParams(backgroundLayoutParams);
    backgroundLayout.setBackgroundColor(Color.BLACK);
    backgroundLayout.setAlpha(0.5f);


    RelativeLayout.LayoutParams contentsTextViewParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.WRAP_CONTENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    contentsTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    contentsTextViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
    contentsTextView = new TextView(context);
    contentsTextView.setLayoutParams(contentsTextViewParams);
    contentsTextView.setTypeface(null, Typeface.BOLD);
    contentsTextView.setTextColor(Color.WHITE);
    contentsTextView.setTextSize(OtherUtils.convertDptoPx(context,24));

    addView(backgroundLayout);
    addView(contentsTextView);
  }

  public void setContents(String contents) {
    contentsTextView.setText(contents);
  }
}
