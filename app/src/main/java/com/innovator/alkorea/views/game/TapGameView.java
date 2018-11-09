package com.innovator.alkorea.views.game;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovator.alkorea.library.utils.OtherUtils;

public class TapGameView extends RelativeLayout {

  private final String TAG = TapGameView.class.getName();

  private Context context;

  private LinearLayout horizontalLayout;
  private Button tapButton;
  private TextView tapTextView, countTextView;

  private int tapCount = 0;

  public TapGameView(Context context) {
    super(context);
    init(context);
  }

  public TapGameView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TapGameView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    RelativeLayout.LayoutParams tapButtonLayoutParams = new RelativeLayout.LayoutParams(
        (int)OtherUtils.convertDpToPx(context, 150),
        (int)OtherUtils.convertDpToPx(context, 150));
    tapButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
    tapButtonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

    tapButton = new Button(context);
    tapButton.setLayoutParams(tapButtonLayoutParams);
    tapButton.setOnClickListener(tapButtonClickListener);

    addView(tapButton);

    RelativeLayout.LayoutParams horizontalLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    //addRule로 인해서 안보일 수 있음 확인해야함
    horizontalLayoutParams.addRule(RelativeLayout.ABOVE, tapButton.getId());

    horizontalLayout = new LinearLayout(context);
    horizontalLayout.setLayoutParams(horizontalLayoutParams);

    RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);

    tapTextView = new TextView(context);
    tapTextView.setLayoutParams(textViewParams);
    tapTextView.setTextSize(OtherUtils.convertDpToPx(context,16));
    tapTextView.setTextColor(Color.BLACK);
    tapTextView.setText("TAP : ");

    countTextView = new TextView(context);
    countTextView.setLayoutParams(textViewParams);
    countTextView.setTextSize(OtherUtils.convertDpToPx(context, 16));
    countTextView.setTextColor(Color.BLACK);
    countTextView.setText(String.valueOf(tapCount));

    horizontalLayout.addView(tapTextView);
    horizontalLayout.addView(countTextView);

    addView(horizontalLayout);
  }

  private View.OnClickListener tapButtonClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      tapCount++;
      countTextView.setText(String.valueOf(tapCount));
    }
  };

  public int getTapCount() {
    return tapCount;
  }

}
