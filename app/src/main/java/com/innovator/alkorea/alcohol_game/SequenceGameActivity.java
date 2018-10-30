package com.innovator.alkorea.alcohol_game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Result;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.AlKoreaTimer;
import com.innovator.alkorea.library.utils.AlKoreaTimerCallbackListener;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.views.GameFinishView;
import com.innovator.alkorea.library.views.GameWaitView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

//Create innovator(JongChan Yang)
public class SequenceGameActivity extends GameActivity implements AlKoreaTimerCallbackListener, SequenceGameView.GameResultCallback {

  private final String TAG = SequenceGameActivity.class.getName();

  private SequenceGameView sequenceGameView;
  private AlKoreaTimer gameTimer;

  private GameFinishView gameFinishView;
  private TextView timeTextView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gameReadyView.getTitleTextView().setText("순서대로");
    gameReadyView.getExplanationTextView().setText("순서대로 빠르게 누르세요.");

    sequenceGameView = new SequenceGameView(getApplicationContext(), this);

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
        gamePlayTime -= 10;
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            timeTextView.setText(OtherUtils.convertDateFormat(gamePlayTime));
          }
        });
      }
    }, gamePlayTimeStadard, 0, 10);
    gameTimer.setCallbackListener(this);
    Log.i(TAG, "gameTime : " + gameTimer.toString());

    gameFinishView = new GameFinishView(getBaseContext());
    gameFinishView.getExitButton().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String roomId = OtherUtils.getSharedPreferencesStringData(getBaseContext(), "roomId", "");
        if (!roomId.isEmpty())
          FirebaseUtils.updateTargetRoomGame(roomId, Room.GAME.NOT);
      }
    });
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
    Log.i(TAG, "qwe111");
    updateTargetPlayerScore();
    updateTargetPlayerState(Room.STATE.FINISH);
  }

  @Override
  public void showGameResult(final HashMap<String, Player> playerList, final HashMap<String, Integer> resultList) {
    Map<String, Integer> descResultList = OtherUtils.sortByComparator(resultList);
    final List<Result> results = new ArrayList<>();
    int rank = 1;
    for (String key : descResultList.keySet()) {
      Result result = new Result();
      result.setRank(String.valueOf(rank));
      result.setPlayerInfo(playerList.get(key));
      int value = resultList.get(key);
      if (value == 0) {
        result.setRank("over");
      }
      else if (value == -3) {
        result.setRank("3out");
      }
      else {
        result.setRank(OtherUtils.convertDateFormat(value));
      }
      results.add(result);
    }
    gameFinishView.setResultList(results);
    rootLayout.removeView(sequenceGameView);
    if(gameFinishView.getParent()!=null)
      ((ViewGroup)gameFinishView.getParent()).removeView(gameFinishView);
    rootLayout.addView(gameFinishView);
  }

  @Override
  public void endGame() {
    updateTargetPlayerState(Room.STATE.ROOM);
    finish();
  }

  @Override
  public void gameSuccess() {
    Log.i(TAG, "qwe333");
    setScore(gamePlayTimeStadard - gamePlayTime);
    updateTargetPlayerScore();
  }

  @Override
  public void gameFail() {
    Log.i(TAG, "qwe222");
    setScore(-3);
    updateTargetPlayerScore();
  }
}
