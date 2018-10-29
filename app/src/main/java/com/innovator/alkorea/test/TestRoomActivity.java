package com.innovator.alkorea.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.RoomManager;
import com.innovator.alkorea.alcohol_game.SequenceGameActivity;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.QRCodeUtils;

import java.util.List;

public class TestRoomActivity extends Activity implements RoomManager.RoomEventListener {

  private final String TAG = TestRoomActivity.class.getName();

  private LinearLayout rootVerticalLayout;
  private LinearLayout horizontalLayout;

  private TextView masterNameTextView, playerCountTextView;
  private Button exitButton, gameButton;
  private ImageView qrcodeImageView;
  private RecyclerView playerRecyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private TestPlayerAdapter testPlayerAdapter;

  private RoomManager roomManager;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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

    {
      String roomId = getRoomId();
      if (!roomId.isEmpty())
        qrcodeImageView.setImageBitmap(QRCodeUtils.generateRQCode(roomId));
    }
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
  protected void onStart() {
    super.onStart();
    roomManager = new RoomManager(getBaseContext(), this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (roomManager != null)
      roomManager.removeRoomManager();
  }

  @Override
  public void updateRoomData(String masterName, int playerCount, List<Player> playerList) {
    testPlayerAdapter.setPlayerList(playerList);
    masterNameTextView.setText(masterName);
    playerCountTextView.setText(String.valueOf(playerCount));
  }

  @Override
  public void startGame(Room.GAME game) {
    switch (game) {
      case NOT:
        break;
      case SEQUENCE:
        Intent intent = new Intent(TestRoomActivity.this, SequenceGameActivity.class);
        startActivity(intent);
        break;
    }
  }

  @Override
  public void exitRoom() {
    finish();
  }

  protected String getRoomId() {
    return OtherUtils.getSharedPreferencesStringData(getBaseContext(), "roomId", "");
  }
}
