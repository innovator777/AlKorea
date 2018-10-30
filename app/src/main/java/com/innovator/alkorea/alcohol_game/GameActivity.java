package com.innovator.alkorea.alcohol_game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.GameManager;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.AlKoreaTimer;
import com.innovator.alkorea.library.utils.AlKoreaTimerCallbackListener;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.views.GameReadyView;

import java.util.TimerTask;

//Create innovator(JongChan Yang)
abstract public class GameActivity extends Activity implements GameManager.GameEventListener {

  private final String TAG = GameActivity.class.getName();
  protected final int gamePlayTimeStadard = 10000;

  abstract protected void gameStart();

  AlKoreaTimerCallbackListener alKoreaTimerCallbackListener;

  protected RelativeLayout rootLayout;
  protected GameReadyView gameReadyView;

  protected AlKoreaTimer readyTimer;
  protected GameManager gameManager;

  // millisecond 단위
  protected int gamePlayTime = gamePlayTimeStadard;

  protected int score = 0;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    rootLayout = new RelativeLayout(getApplicationContext());
    rootLayout.setLayoutParams(rootLayoutParams);
    setContentView(rootLayout);

    gameReadyView = new GameReadyView(getApplicationContext());
    alKoreaTimerCallbackListener = new AlKoreaTimerCallbackListener() {
      @Override
      public void stopTimer() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            rootLayout.removeView(gameReadyView);
          }
        });
        gameStart();
      }
    };

    readyTimer = new AlKoreaTimer(new TimerTask() {
      @Override
      public void run() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            gameReadyView.getNumberCountTextView().setText(gameReadyView.getCountArray()[gameReadyView.getCountIndex()]);
            gameReadyView.upCountIndex();
          }
        });
      }
    }, 4000, 0, 1000);
    readyTimer.setCallbackListener(alKoreaTimerCallbackListener);
  }

  @Override
  protected void onStart() {
    super.onStart();
    gameManager = new GameManager(getBaseContext(), GameActivity.this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (gameManager != null)
      gameManager.removeDatabaseReferenceEventListener();
  }

  protected void updateTargetPlayerState(Room.STATE state) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", "");
    if(!roomId.isEmpty()) {
      FirebaseUtils.updateTargetRoomPlayerState(roomId, uid, state);
    }
  }

  protected void updateTargetPlayerScore() {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", "");
    if(!roomId.isEmpty()) {
      FirebaseUtils.updateTargetRoomPlayerScore(roomId, uid, score);
    }
  }

  protected void setScore(int score) {
    this.score = score;
  }
}
