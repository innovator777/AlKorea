package com.innovator.alkorea.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.innovator.alkorea.alcohol_game.GameActivity;
import com.innovator.alkorea.alcohol_game.SequenceGameActivity;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.QRCodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestRoomActivity extends Activity {

  private final String TAG = TestRoomActivity.class.getName();

  private LinearLayout rootVerticalLayout;
  private LinearLayout horizontalLayout;

  private TextView masterNameTextView, playerCountTextView;
  private Button exitButton, gameButton;
  private ImageView qrcodeImageView;
  private RecyclerView playerRecyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private TestPlayerAdapter testPlayerAdapter;

  private DatabaseReference databaseReference;
  private ValueEventListener valueEventListener;

  private Room roomInfo;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", "");
    if (!roomId.isEmpty()) {
      databaseReference  = FirebaseUtils.getDBTargetRoomIDReference(roomId);
      valueEventListener = FirebaseUtils.getDBTargetRoomDataWithListener(roomId, databaseReference, roomCallback);
    }
    LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);
    rootVerticalLayout = new LinearLayout(getApplicationContext());
    rootVerticalLayout.setLayoutParams(rootLayoutParams);
    rootVerticalLayout.setOrientation(LinearLayout.VERTICAL);
    setContentView(rootVerticalLayout);

    LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    horizontalLayout = new LinearLayout(getApplicationContext());
    horizontalLayout.setLayoutParams(horizontalLayoutParams);
    horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
    horizontalLayout.setBackgroundColor(Color.RED);

    LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);

    masterNameTextView = new TextView(getApplicationContext());
    masterNameTextView.setLayoutParams(textViewParams);
    masterNameTextView.setTextColor(Color.BLACK);

    playerCountTextView = new TextView(getApplicationContext());
    playerCountTextView.setLayoutParams(textViewParams);
    playerCountTextView.setTextColor(Color.BLACK);

    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);

    exitButton = new Button(getApplicationContext());
    exitButton.setLayoutParams(buttonParams);
    exitButton.setText("나가기");

    exitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        exitRoom();
      }
    });

    gameButton = new Button(getApplicationContext());
    gameButton.setLayoutParams(buttonParams);
    gameButton.setText("순서대로 게임");

    gameButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", "");
        if (!roomId.isEmpty()) {
          FirebaseUtils.updateTargetRoomGame(roomId, Room.GAME.SEQUENCE);
        }
      }
    });


    horizontalLayout.addView(masterNameTextView);
    horizontalLayout.addView(playerCountTextView);
    horizontalLayout.addView(exitButton);
    horizontalLayout.addView(gameButton);

    rootVerticalLayout.addView(horizontalLayout);

    LinearLayout.LayoutParams qrcodeImageViewParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);

    qrcodeImageView = new ImageView(getApplicationContext());
    qrcodeImageView.setLayoutParams(qrcodeImageViewParams);
    if (!roomId.isEmpty())
      qrcodeImageView.setImageBitmap(QRCodeUtils.generateRQCode(roomId));

    rootVerticalLayout.addView(qrcodeImageView);

    LinearLayout.LayoutParams playerRecyclerViewParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);

    playerRecyclerView = new RecyclerView(getApplicationContext());
    playerRecyclerView.setLayoutParams(playerRecyclerViewParams);
    playerRecyclerView.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    playerRecyclerView.setLayoutManager(layoutManager);

    testPlayerAdapter = new TestPlayerAdapter();
    playerRecyclerView.setAdapter(testPlayerAdapter);
    rootVerticalLayout.addView(playerRecyclerView);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    removeDatabaseReferenceEventListener();
  }

  private void exitRoom() {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", "");
    if(!roomId.isEmpty()) {
      removeDatabaseReferenceEventListener();
      if (uid.equals(roomInfo.getMasterUID())) {
        FirebaseUtils.removeTargetRoom(roomId);
      }else {
        FirebaseUtils.exitTargetRoom(roomId, uid);
      }
      OtherUtils.removeSharedPreferenceData(getApplicationContext(), "roomId");
    }
    finish();
  }

  private void removeDatabaseReferenceEventListener() {
    if (databaseReference != null && valueEventListener != null)
      databaseReference.removeEventListener(valueEventListener);
  }

  private void checkGame(Room.GAME game) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(getApplicationContext(), "roomId", "");
    if (!roomId.isEmpty()) {
      switch (game) {
        case NOT:
          break;
        case SEQUENCE:
          //플레이 유저의 게임상태 업데이트
          FirebaseUtils.updateTargetRoomPlayerState(roomId, uid, Room.STATE.GAME);
          Intent intent = new Intent(TestRoomActivity.this, SequenceGameActivity.class);
          startActivity(intent);
          break;
      }
    }
  }

  // 삳태가 없거나 ROOM일때 False 반환 => 방에 입장 후 첫게임을 하기 전이거나 게임 종료 후 방으로 나온 경우
  // 상태가 GAME 이거나 FINISH 이면 TRUE 반환 => 게임 중이거나 결과 화면
  private boolean checkPlayerState(HashMap<String, Room.STATE> playerState) {
    if (playerState != null) {
      String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
      return playerState.get(uid).equals(Room.STATE.GAME) || playerState.get(uid).equals(Room.STATE.FINISH);
    }
    return false;
  }

  FirebaseUtils.RoomCallback roomCallback = new FirebaseUtils.RoomCallback() {
    @Override
    public void getRoomDataCallback(Room room) {
      if (room != null) {
        String masterName = "";
        List<Player> players = new ArrayList<>();
        roomInfo = room;
        for (Iterator<String> it = room.getPlayers().keySet().iterator(); it.hasNext(); ) {
          String key = it.next();
          players.add(room.getPlayers().get(key));
          if (key.equals(room.getMasterUID())) {
            masterName = room.getPlayers().get(key).getName();
          }
        }
        testPlayerAdapter.setPlayers(players);
        masterNameTextView.setText(masterName);
        playerCountTextView.setText("" + players.size());

        if (!checkPlayerState(room.getPlayerState()))
          checkGame(room.getGame());
      }
      else {
        exitRoom();
      }
    }

    @Override
    public void cancelledCallback(DatabaseError error) {
      Log.i(TAG, error.getMessage());
    }
  };
}
