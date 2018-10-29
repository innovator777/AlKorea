package com.innovator.alkorea;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RoomManager {

  private final String TAG  = RoomManager.class.getName();

  private Context context;

  private DatabaseReference roomDatabaseReference;
  private ValueEventListener roomValueEventListener;
  private RoomEventListener roomEventListener;

  private Room roomInfo;
  private String masterName;
  private int playerCount;
  private List<Player> playerList;


  public interface RoomEventListener {
    void updateRoomData(String masterName, int playerCount, List<Player> playerList);
    void startGame(Room.GAME game);
    void exitRoom();
  }

  public RoomManager(Context context, RoomEventListener roomEventListener) {
    this.context = context;
    String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", "");
    if (!roomId.isEmpty()) {
      roomDatabaseReference = FirebaseUtils.getDBTargetRoomIDReference(roomId);
      roomValueEventListener = FirebaseUtils.getDBTargetRoomDataWithListener(roomId, roomDatabaseReference, roomCallback);
    }
    this.roomEventListener = roomEventListener;
  }

  FirebaseUtils.RoomCallback roomCallback = new FirebaseUtils.RoomCallback() {
    @Override
    public void getRoomDataCallback(Room room) {
      if (room != null) {
        if (!roomInfo.isEqualsRoomBasicData(room)) {
          List<Player> list = new ArrayList<>();
          for (Iterator<String> it = room.getPlayerList().keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            list.add(room.getPlayerList().get(key));
            if (key.equals(room.getMasterUID())) {
              masterName = room.getPlayerList().get(key).getName();
            }
          }
          playerList = new ArrayList<>(list);
          playerCount = playerList.size();
          roomEventListener.updateRoomData(masterName, playerCount, playerList);
        }
        if (!checkPlayerState(room.getPlayerState()))
          checkGame(room.getGame());

        roomInfo = room;
      }
      else {
        removeRoomManager();
        roomEventListener.exitRoom();
      }
    }

    @Override
    public void cancelledCallback(DatabaseError error) {
      Log.i(TAG, error.getMessage());
    }
  };

  public void removeRoomManager() {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", "");
    if(!roomId.isEmpty()) {
      if (uid.equals(masterName)) {
        FirebaseUtils.removeTargetRoom(roomId);
      } else {
        FirebaseUtils.exitTargetRoom(roomId, uid);
      }
      OtherUtils.removeSharedPreferenceData(context, "roomId");
    }
    removeDatabaseReferenceEventListener();
  }

  private void removeDatabaseReferenceEventListener() {
    if (roomDatabaseReference != null && roomValueEventListener != null)
      roomDatabaseReference.removeEventListener(roomValueEventListener);
  }


  private void checkGame(Room.GAME game) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", "");
    if (!roomId.isEmpty()) {
      if (Room.GAME.NOT != game) {
        FirebaseUtils.updateTargetRoomPlayerState(roomId, uid, Room.STATE.GAME);
        roomEventListener.startGame(game);
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

  public DatabaseReference getRoomDatabaseReference() {
    return roomDatabaseReference;
  }

  public void setRoomDatabaseReference(DatabaseReference roomDatabaseReference) {
    this.roomDatabaseReference = roomDatabaseReference;
  }

  public ValueEventListener getRoomValueEventListener() {
    return roomValueEventListener;
  }

  public void setRoomValueEventListener(ValueEventListener roomValueEventListener) {
    this.roomValueEventListener = roomValueEventListener;
  }

  public Room getRoomInfo() {
    return roomInfo;
  }

  public void setRoomInfo(Room roomInfo) {
    this.roomInfo = roomInfo;
  }

  public String getMasterName() {
    return masterName;
  }

  public void setMasterName(String masterName) {
    this.masterName = masterName;
  }

  public int getPlayerCount() {
    return playerCount;
  }

  public void setPlayerCount(int playerCount) {
    this.playerCount = playerCount;
  }

  public List<Player> getPlayerList() {
    return playerList;
  }

  public void setPlayerList(List<Player> playerList) {
    this.playerList = playerList;
  }

}
