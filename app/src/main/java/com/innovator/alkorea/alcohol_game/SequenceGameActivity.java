package com.innovator.alkorea.alcohol_game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.AlKoreaTimer;
import com.innovator.alkorea.library.utils.AlKoreaTimerCallbackListener;
import com.innovator.alkorea.library.utils.OtherUtils;

import java.util.HashMap;
import java.util.TimerTask;

//Create innovator(JongChan Yang)
public class SequenceGameActivity extends GameActivity implements AlKoreaTimerCallbackListener {

  private final String TAG = SequenceGameActivity.class.getName();

  private SequenceGameView sequenceGameView;
  private AlKoreaTimer gameTimer;

  private TextView timeTextView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gameReadyView.getTitleTextView().setText("순서대로");
    gameReadyView.getExplanationTextView().setText("순서대로 빠르게 누르세요.");

    sequenceGameView = new SequenceGameView(getApplicationContext());

    RelativeLayout.LayoutParams timeTextViewParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.WRAP_CONTENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    timeTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    timeTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

    timeTextView = new TextView(getApplicationContext());
    timeTextView.setLayoutParams(timeTextViewParams);
    timeTextView.setText(OtherUtils.convertDateFormat(gamePlayTime));
    sequenceGameView.addView(timeTextView);

    rootLayout.addView(sequenceGameView);
    rootLayout.addView(gameReadyView);
    readyTimer.start();

    gameTimer = new AlKoreaTimer(new TimerTask() {
      @Override
      public void run() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            gamePlayTime -= 10;
            timeTextView.setText(OtherUtils.convertDateFormat(gamePlayTime));
          }
        });
      }
    }, 10000, 0, 10);
    gameTimer.setCallbackListener(this);
  }

  @Override
  protected void gameStart() {
    gameTimer.start();
  }

  @Override
  public void stopTimer() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        timeTextView.setText("00.000");
      }
    });
    updateTargetPlayerScore();
    updateTargetPlayerState(Room.STATE.FINISH);
  }

  @Override
  public void showGameResult(HashMap<String, Integer> result) {

  }

  @Override
  public void endGame() {
    finish();
  }
}
