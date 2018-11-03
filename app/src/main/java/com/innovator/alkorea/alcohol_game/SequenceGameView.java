package com.innovator.alkorea.alcohol_game;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.innovator.alkorea.library.views.GameWaitView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Create innovator(JongChan Yang)
public class SequenceGameView extends RelativeLayout {

  private final String TAG = SequenceGameView.class.getName();

  public interface GameResultCallback {
    void gameSuccess();
    void gameFail();
  }

  private GameResultCallback gameResultCallback;

  private final int row = 3;
  private final int column = 5;
  private final int life = 3;
  private int beforeNumber = 0;
  private int lifeIndex = 0;

  private Context context;
  private int[][] numberSlot;
  private int[] lifeSlot;

  private LinearLayout horizontalLinearLayout;
  private LinearLayout[] verticalLinearLayout;
  private Button[][] buttonSlot;
  private LinearLayout lifeLinearLayout;
  private ImageView[] lifeViewSlot;
  private GameWaitView gameWaitView;

  public SequenceGameView(Context context, GameResultCallback gameResultCallback) {
    super(context);
    this.gameResultCallback = gameResultCallback;
    init(context);
  }

  public SequenceGameView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public SequenceGameView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    this.context = context;
    numberSlot = new int[row][column];
    verticalLinearLayout = new LinearLayout[row];
    buttonSlot = new Button[row][column];
    lifeSlot = new int[]{0, 0, 0};
    lifeViewSlot = new ImageView[life];
    setRandomDataInSlot(numberSlot);
    setUI();
  }

  private void setUI() {
    RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    setLayoutParams(rootLayoutParams);

    // create lifeLinearLayout
    RelativeLayout.LayoutParams lifeLinearLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    lifeLinearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    lifeLinearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

    lifeLinearLayout = new LinearLayout(context);
    lifeLinearLayout.setLayoutParams(lifeLinearLayoutParams);
    lifeLinearLayout.setOrientation(LinearLayout.HORIZONTAL);


    // create lifeView & addView
    LinearLayout.LayoutParams lifeViewParams = new LinearLayout.LayoutParams(
//        LinearLayout.LayoutParams.WRAP_CONTENT,
//        LinearLayout.LayoutParams.WRAP_CONTENT
        50, 50
    );
    lifeViewParams.leftMargin = 10;
    lifeViewParams.topMargin = 10;
    lifeViewParams.rightMargin = 10;

    for (int i = 0; i < lifeViewSlot.length; i++) {
      lifeViewSlot[i] = new ImageView(context);
      lifeViewSlot[i].setLayoutParams(lifeViewParams);
      lifeViewSlot[i].setBackgroundColor(Color.RED);
      lifeLinearLayout.addView(lifeViewSlot[i]);
    }
    addView(lifeLinearLayout);

    //create horizontalLinearLayout
    RelativeLayout.LayoutParams horizontalLinearLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    horizontalLinearLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
    horizontalLinearLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

    horizontalLinearLayout = new LinearLayout(context);
    horizontalLinearLayout.setLayoutParams(horizontalLinearLayoutParams);
    horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

    //create verticalLinearLayout
    LinearLayout.LayoutParams verticalLinearLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);

    verticalLinearLayout = new LinearLayout[row];
    for (int i = 0; i < row; i++) {
      verticalLinearLayout[i] = new LinearLayout(context);
      verticalLinearLayout[i].setLayoutParams(verticalLinearLayoutParams);
      verticalLinearLayout[i].setOrientation(LinearLayout.VERTICAL);
    }

    //create Buttons
    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    buttonParams.leftMargin = 10;
    buttonParams.topMargin = 10;
    buttonParams.rightMargin = 10;
    buttonParams.bottomMargin = 10;

    for (int r = 0; r < row; r++) {
      for (int c = 0; c < column; c++) {
        buttonSlot[r][c] = new Button(context);
        buttonSlot[r][c].setLayoutParams(buttonParams);
        buttonSlot[r][c].setTextColor(Color.WHITE);
//        buttonSlot[r][c].setText(String.valueOf(numberSlot[r][c]));
        buttonSlot[r][c].setOnClickListener(buttonClickListener);
      }
    }

    //addVIew
    for (int r = 0; r < row; r++) {
      LinearLayout linearLayout = verticalLinearLayout[r];
      for(int c = 0; c < column; c++) {
        linearLayout.addView(buttonSlot[r][c]);
      }
      horizontalLinearLayout.addView(linearLayout);
    }

    addView(horizontalLinearLayout);

    gameWaitView = new GameWaitView(context);
  }

  private void setRandomDataInSlot(int[][] slot) {
    Random random = new Random();
    List list = new ArrayList<Integer>();
    while (list.size() < row * column) {
      int number = random.nextInt(row * column) + 1;
      if (!list.contains(number))
        list.add(number);
    }

    for (int i = 0; i < slot.length; i++) {
      for (int j = 0; j < slot[i].length; j++) {
        slot[i][j] = (Integer) list.get((i * column) + j);
      }
    }
  }

  public void setButtonNumber() {
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < column; c++) {
        buttonSlot[r][c].setText(String.valueOf(numberSlot[r][c]));
      }
    }
  }

  View.OnClickListener buttonClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      Button button = (Button)view;
      if (button != null) {
        int number = Integer.parseInt(button.getText().toString());
        if (number > 0) {
          if (number - beforeNumber == 1) {
            button.setVisibility(View.INVISIBLE);
            beforeNumber = number;
            if (number == 15) {
              gameWaitView.setContents("잘 하셨습니다. 다른 플레이어가 끝나기를 기다려주세요.");
              if(gameWaitView.getParent()!=null)
                ((ViewGroup)gameWaitView.getParent()).removeView(gameWaitView);
              addView(gameWaitView);
              gameResultCallback.gameSuccess();
            }
          }
          else {
            if (lifeIndex < life) {
              lifeSlot[lifeIndex] = 1;
              lifeViewSlot[lifeIndex].setBackgroundColor(Color.BLACK);
              lifeIndex++;
              if (lifeIndex == 3) {
                gameWaitView.setContents("실패하셨습니다. 한잔 드시죠 ^^7 다른 플레이어가 끝나기를 기다려주세요.");
                if(gameWaitView.getParent()!=null)
                  ((ViewGroup)gameWaitView.getParent()).removeView(gameWaitView);
                addView(gameWaitView);
                gameResultCallback.gameFail();
              }
            }
          }
        }
      }
    }
  };
}
