package com.innovator.alkorea.views.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.models.Player;
import com.innovator.alkorea.models.Result;
import com.innovator.alkorea.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.timer.GameTimer;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.timer.ReadyTimer;
import com.innovator.alkorea.library.views.GameFinishView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TapGameActivity extends GameActivity {

  protected final String TAG = TapGameActivity.class.getName();

  private TapGameView tapGameView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    gameReadyView.getTitleTextView().setText("탭탭게임");
    gameReadyView.getExplanationTextView().setText("10초동안 화면을 마구마구 터치하세요.");

    tapGameView = new TapGameView(getBaseContext());
    gameFinishView = new GameFinishView(getBaseContext(), this);

    tapGameView.addView(timeTextView);
    OtherUtils.enableDisableViewGroup(tapGameView, false);

    rootLayout.addView(tapGameView);
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
  public void showGameResult(String masterUid, HashMap<String, Player> playerList, HashMap<String, Integer> resultList) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Map<String, Integer> descResultList = OtherUtils.sortByComparator(resultList, false);
    final List<Result> results = new ArrayList<>();
    int rank = 1;
    for (String key : descResultList.keySet()) {
      Result result = new Result();
      result.setPlayerInfo(playerList.get(key));
      if (resultList.get(key) == 0)
        result.setRank("벌칙");
      else
        result.setRank(String.valueOf(rank) + "등");
      result.setResult(String.valueOf(resultList.get(key)) + "개");
      results.add(result);
      rank++;
    }
    results.get(results.size()-1).setRank("벌칙");
    if (!uid.equals(masterUid))
      gameFinishView.enableExitButton(false);
    gameFinishView.setResultList(results);
    rootLayout.removeView(tapGameView);
    if(gameFinishView.getParent()!=null)
      ((ViewGroup)gameFinishView.getParent()).removeView(gameFinishView);
    rootLayout.addView(gameFinishView);
  }

  @Override
  public void endTimer(String tag) {
    if(tag.equals(ReadyTimer.class.getName())) {
      readyTimer.stopTimer();
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          rootLayout.removeView(gameReadyView);
          OtherUtils.enableDisableViewGroup(tapGameView, true);
        }
      });
      gameTimer.startTimer();
    }
    else {
      setScore(tapGameView.getTapCount());
      updateTargetPlayerScore();
      updateTargetPlayerState(Room.STATE.FINISH);
      gameTimer.stopTimer();
    }

  }

  @Override
  public void goRoom() {
    String roomId = OtherUtils.getSharedPreferencesStringData(getBaseContext(), "roomId", null);
    if (roomId != null)
      FirebaseUtils.updateTargetRoomGame(roomId, Room.GAME.NOT);
  }
}
