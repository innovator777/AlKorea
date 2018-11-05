package com.innovator.alkorea.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Result;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.AlKoreaTimerCallbackListener;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.GameTimer;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.ReadyTimer;
import com.innovator.alkorea.library.views.GameFinishView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Create innovator(JongChan Yang)
public class SequenceGameActivity extends GameActivity implements AlKoreaTimerCallbackListener, SequenceGameView.GameResultCallback {

  private final String TAG = SequenceGameActivity.class.getName();

  private SequenceGameView sequenceGameView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gameReadyView.getTitleTextView().setText("순서대로");
    gameReadyView.getExplanationTextView().setText("순서대로 빠르게 누르세요.");

    sequenceGameView = new SequenceGameView(getBaseContext(), this);
    gameFinishView = new GameFinishView(getBaseContext(), this);

    sequenceGameView.addView(timeTextView);
    OtherUtils.enableDisableViewGroup(sequenceGameView, false);

    rootLayout.addView(sequenceGameView);
    rootLayout.addView(gameReadyView);
    readyTimer.startTimer();

    gameTimer = new GameTimer(gameHandler, this);
  }

  private Handler gameHandler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message message) {
      int value = message.arg1;
      timeTextView.setText(OtherUtils.convertDateFormat(value));
      gamePlayTime = value;
      return false;
    }
  });

  @Override
  public void showGameResult(final HashMap<String, Player> playerList, final HashMap<String, Integer> resultList) {
    Map<String, Integer> descResultList = OtherUtils.sortByComparator(resultList, false);
    final List<Result> results = new ArrayList<>();
    int rank = 1;
    for (String key : descResultList.keySet()) {
      Result result = new Result();
      result.setRank(String.valueOf(rank));
      result.setPlayerInfo(playerList.get(key));
      int value = resultList.get(key);
      if (value == 0) {
        result.setResult("over");
      }
      else if (value == -3) {
        result.setResult("3out");
      }
      else {
        result.setResult(OtherUtils.convertDateFormat(value));
      }
      results.add(result);
      rank++;
    }
    gameFinishView.setResultList(results);
    rootLayout.removeView(sequenceGameView);
    if(gameFinishView.getParent()!=null)
      ((ViewGroup)gameFinishView.getParent()).removeView(gameFinishView);
    rootLayout.addView(gameFinishView);
  }


  @Override
  public void gameSuccess() {
    setScore(gamePlayTimeStadard - gamePlayTime);
    updateTargetPlayerScore();
  }

  @Override
  public void gameFail() {
    setScore(-3);
    updateTargetPlayerScore();
  }


  @Override
  public void endTimer(String tag) {
    if (tag.equals(ReadyTimer.class.getName())) {
      readyTimer.stopTimer();
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          sequenceGameView.setButtonNumber();
          rootLayout.removeView(gameReadyView);
          OtherUtils.enableDisableViewGroup(sequenceGameView, true);
          gameTimer.startTimer();
        }
      });
    }
    else {
      updateTargetPlayerScore();
      updateTargetPlayerState(Room.STATE.FINISH);
      gameTimer.stopTimer();
    }
  }

  @Override
  public void goRoom() {
    String roomId = OtherUtils.getSharedPreferencesStringData(getBaseContext(), "roomId", "");
    if (!roomId.isEmpty())
      FirebaseUtils.updateTargetRoomGame(roomId, Room.GAME.NOT);
  }
}
