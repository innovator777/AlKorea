package com.innovator.alkorea.views.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.manager.GameManager;
import com.innovator.alkorea.models.Room;
import com.innovator.alkorea.library.utils.timer.AlKoreaTimerCallbackListener;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.timer.GameTimer;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.timer.ReadyTimer;
import com.innovator.alkorea.library.views.GameFinishCallbackListener;
import com.innovator.alkorea.library.views.GameFinishView;
import com.innovator.alkorea.library.views.GameReadyView;

//Create innovator(JongChan Yang)
abstract public class GameActivity extends Activity implements GameManager.GameEventListener,
                                                                AlKoreaTimerCallbackListener,
                                                                GameFinishCallbackListener {

  private final String TAG = GameActivity.class.getName();
  protected final int gamePlayTimeStadard = 10000;

  protected RelativeLayout rootLayout;
  protected GameReadyView gameReadyView;

  protected ReadyTimer readyTimer;
  protected GameTimer gameTimer;
  protected GameManager gameManager;
  protected GameFinishView gameFinishView;
  protected TextView timeTextView;

  // millisecond 단위
  protected int gamePlayTime = gamePlayTimeStadard;

  protected int score = 0;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gameManager = new GameManager(getBaseContext(), GameActivity.this);

    RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    rootLayout = new RelativeLayout(getApplicationContext());
    rootLayout.setLayoutParams(rootLayoutParams);
    setContentView(rootLayout);

    gameReadyView = new GameReadyView(getApplicationContext());
    readyTimer = new ReadyTimer(readyHander, GameActivity.this);

    RelativeLayout.LayoutParams timeTextViewParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    timeTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    timeTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

    timeTextView = new TextView(getBaseContext());
    timeTextView.setLayoutParams(timeTextViewParams);
    timeTextView.setText(OtherUtils.convertDateFormat(gamePlayTime));
    timeTextView.setTextColor(Color.BLACK);
  }

  private Handler readyHander = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message message) {
      int value = message.arg1;
      gameReadyView.getNumberCountTextView().setText(gameReadyView.getCountArray()[value]);
      return false;
    }
  });

  @Override
  protected void onStop() {
    super.onStop();
    if (gameManager != null)
      gameManager.removeDatabaseReferenceEventListener();
  }

  @Override
  public void endGame() {
    finish();
  }

  @Override
  public void onBackPressed() {

  }

  protected void updateTargetPlayerState(Room.STATE state) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", null);
    if(roomId != null) {
      FirebaseUtils.updateTargetRoomPlayerState(roomId, uid, state);
    }
  }

  protected void updateTargetPlayerScore() {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", null);
    if(roomId != null) {
      FirebaseUtils.updateTargetRoomPlayerScore(roomId, uid, score);
    }
  }

  protected void setScore(int score) {
    this.score = score;
  }
}
