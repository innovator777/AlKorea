package com.innovator.alkorea.library.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerView extends RelativeLayout {

  private final String TAG = PlayerView.class.getName();

  private Context context;

  private ImageView backgroundImageView;
  private TextView textView;

  public PlayerView(Context context) {
    super(context);
    init(context);
  }

  public PlayerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void init(Context context){
    this.context = context;
    setUI();
  }

  private void setUI() {
    RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    setLayoutParams(rootLayoutParams);

    RelativeLayout.LayoutParams backgroundImageViewParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);

    backgroundImageView = new ImageView(context);
    backgroundImageView.setLayoutParams(backgroundImageViewParams);
    backgroundImageView.setBackgroundColor(Color.RED);

    RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    textViewParams.addRule(RelativeLayout.CENTER_VERTICAL);

    textView = new TextView(context);
    textView.setLayoutParams(textViewParams);

    addView(backgroundImageView);
    addView(textView);
  }

  public ImageView getBackgroundImageView() {
    return backgroundImageView;
  }

  public TextView getTextView(){
    return textView;
  }
}
