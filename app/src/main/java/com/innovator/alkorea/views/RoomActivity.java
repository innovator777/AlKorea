package com.innovator.alkorea.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.R;
import com.innovator.alkorea.models.Player;
import com.innovator.alkorea.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.QRCodeUtils;
import com.innovator.alkorea.manager.RoomManager;
import com.innovator.alkorea.views.adapter.GameAdapter;
import com.innovator.alkorea.views.adapter.PlayerAdapter;
import com.innovator.alkorea.views.game.SequenceGameActivity;
import com.innovator.alkorea.views.game.TapGameActivity;

import java.util.List;

//Create innovator(JongChan Yang)
public class RoomActivity extends Activity implements RoomManager.RoomEventListener, GameAdapter.GameStartListener {

  private final String TAG = RoomActivity.class.getName();

  private TextView masterNameTextView, playerCountTextView;
  private Button exitButton;
  private ImageView qrcodeImageView;

  private RecyclerView playerRecyclerView;
  private PlayerAdapter playerAdapter;
  private RecyclerView.LayoutManager playerAdapaterLayoutManager;

  private RecyclerView gameRecyclerView;
  private GameAdapter gameAdapter;
  private RecyclerView.LayoutManager gameAdpaterLayoutManager;

  private RoomManager roomManager;

  private String uid;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_room);

    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    masterNameTextView = findViewById(R.id.master_name_textview);
    playerCountTextView = findViewById(R.id.player_count_textview);
    exitButton = findViewById(R.id.exit_button);

    exitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        exitRoom();
      }
    });

    qrcodeImageView = findViewById(R.id.qrcode_imageview);

    {
      String roomId = getRoomId();
      if (!roomId.isEmpty())
        qrcodeImageView.setImageBitmap(QRCodeUtils.generateRQCode(roomId));
    }



    playerRecyclerView = findViewById(R.id.player_recyclerview);
    playerAdapaterLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    playerRecyclerView.setHasFixedSize(true);
    playerRecyclerView.setLayoutManager(playerAdapaterLayoutManager);

    playerAdapter = new PlayerAdapter();
    playerRecyclerView.setAdapter(playerAdapter);


    gameRecyclerView = findViewById(R.id.game_recyclerview);
    gameAdpaterLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    gameRecyclerView.setHasFixedSize(true);
    gameRecyclerView.setLayoutManager(gameAdpaterLayoutManager);

    gameAdapter = new GameAdapter(this);
    gameRecyclerView.setAdapter(gameAdapter);

  }


  @Override
  protected void onStart() {
    super.onStart();
    if (roomManager != null) {
      roomManager.removeDatabaseReferenceEventListener();
    }
    roomManager = new RoomManager(getBaseContext(), this);
    FirebaseUtils.updateTargetRoomPlayerState(getRoomId(), uid, Room.STATE.ROOM);
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (roomManager != null)
      roomManager.removeDatabaseReferenceEventListener();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (roomManager != null)
      roomManager.removeRoomManager();
  }

  @Override
  public void updateRoomData(String masterUid, String masterName, int playerCount, List<Player> playerList) {
    playerAdapter.setPlayerList(playerList);
    if (uid.equals(masterUid))
      gameAdapter.setMaster(true);
    masterNameTextView.setText("방장 : " + masterName);
    playerCountTextView.setText("플레이어 수 : " + String.valueOf(playerCount));
  }

  @Override
  public void startGame(Room.GAME game) {
    switch (game) {
      case NOT:
        break;
      case SEQUENCE:
        startActivity(new Intent(RoomActivity.this, SequenceGameActivity.class));
        break;
      case TAP:
        startActivity(new Intent(RoomActivity.this, TapGameActivity.class));
        break;
    }
  }

  @Override
  public void exitRoom() {
    if (roomManager != null)
      roomManager.removeRoomManager();
    finish();
  }

  protected String getRoomId() {
    return OtherUtils.getSharedPreferencesStringData(getBaseContext(), "roomId", null);
  }

  @Override
  public void gameSelect(Room.GAME game) {
    String roomId = getRoomId();
    if (roomId != null)
      FirebaseUtils.updateTargetRoomGame(roomId, game);
  }
}
