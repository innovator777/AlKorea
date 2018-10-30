package com.innovator.alkorea.library.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovator.alkorea.library.adapter.GameResultAdapter;
import com.innovator.alkorea.library.models.Result;
import com.innovator.alkorea.library.utils.OtherUtils;

import java.util.List;

//Create innovator(JongChan Yang)
public class GameFinishView extends RelativeLayout {

  private final String TAG = GameFinishView.class.getName();

  private Context context;
  private RelativeLayout backgroundLayout;
  private LinearLayout mainLinearLayout, topLinearLayout, bottomLinearLayout;
  private TextView finishTextView, resultTextView;
  private LinearLayout lineLayout;
  private Button exitButton;

  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;

  private GameResultAdapter gameResultAdapter;
  private List<Result> resultList;

  public GameFinishView(Context context) {
    super(context);
    init(context);
  }

  public GameFinishView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public GameFinishView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    this.context = context;
    setUI();
  }

  public void setUI() {
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

    LinearLayout.LayoutParams mainLinearLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);

    mainLinearLayout = new LinearLayout(context);
    mainLinearLayout.setLayoutParams(mainLinearLayoutParams);
    mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
    mainLinearLayout.setWeightSum(15);

    LinearLayout.LayoutParams topLinearLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT, 14);

    topLinearLayout = new LinearLayout(context);
    topLinearLayout.setLayoutParams(topLinearLayoutParams);
    topLinearLayout.setOrientation(LinearLayout.VERTICAL);

    LinearLayout.LayoutParams bottomLinearLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT, 1);

    bottomLinearLayout = new LinearLayout(context);
    bottomLinearLayout.setLayoutParams(bottomLinearLayoutParams);

    addView(backgroundLayout);
    addView(mainLinearLayout);
    mainLinearLayout.addView(topLinearLayout);
    mainLinearLayout.addView(bottomLinearLayout);

    LinearLayout.LayoutParams finishTextViewParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    finishTextViewParams.gravity = Gravity.CENTER;

    finishTextView = new TextView(context);
    finishTextView.setLayoutParams(finishTextViewParams);
    finishTextView.setTypeface(null, Typeface.BOLD);
    finishTextView.setTextColor(Color.WHITE);
    finishTextView.setTextSize(OtherUtils.convertDptoPx(context,42));
    finishTextView.setText("Finish");

    LinearLayout.LayoutParams resultTextViewParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    resultTextViewParams.gravity = Gravity.CENTER;

    resultTextView = new TextView(context);
    resultTextView.setLayoutParams(resultTextViewParams);
    resultTextView.setTypeface(null, Typeface.BOLD);
    resultTextView.setTextColor(Color.WHITE);
    resultTextView.setTextSize(OtherUtils.convertDptoPx(context,30));
    resultTextView.setText("Result");

    LinearLayout.LayoutParams lineLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        (int)OtherUtils.convertDptoPx(context,1));

    lineLayout = new LinearLayout(context);
    lineLayout.setLayoutParams(lineLayoutParams);
    lineLayout.setBackgroundColor(Color.WHITE);

    LinearLayout.LayoutParams recyclerViewParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);

    recyclerView = new RecyclerView(context);
    recyclerView.setLayoutParams(recyclerViewParams);
    recyclerView.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    gameResultAdapter = new GameResultAdapter();
    recyclerView.setAdapter(gameResultAdapter);
    gameResultAdapter.setResultList(resultList);

    topLinearLayout.addView(finishTextView);
    topLinearLayout.addView(resultTextView);
    topLinearLayout.addView(lineLayout);
    topLinearLayout.addView(recyclerView);

    LinearLayout.LayoutParams exitButtonParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);
    exitButton = new Button(context);
    exitButton.setLayoutParams(exitButtonParams);
    exitButton.setText("나가기");

    bottomLinearLayout.addView(exitButton);
  }

  public void setResultList(List<Result> resultList) {
    this.resultList = resultList;
    gameResultAdapter.setResultList(resultList);
    gameResultAdapter.notifyDataSetChanged();
  }

  public Button getExitButton() {
    return exitButton;
  }

}
