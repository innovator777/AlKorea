package com.innovator.alkorea.manager;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.innovator.alkorea.models.Player;
import com.innovator.alkorea.models.Room;
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
  private String masterUid;
  private int playerCount;
  private List<Player> playerList;
  private String myUid;


  public interface RoomEventListener {
    void updateRoomData(String masterUid, String masterName, int playerCount, List<Player> playerList);
    void startGame(Room.GAME game);
    void exitRoom();
  }

  public RoomManager(Context context, RoomEventListener roomEventListener) {
    this.context = context;
    String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", null);
    if (roomId != null) {
      roomDatabaseReference = FirebaseUtils.getDBTargetRoomIDReference(roomId);
      roomValueEventListener = FirebaseUtils.getDBTargetRoomDataWithListener(roomId, roomDatabaseReference, roomCallback);
    }
    this.roomEventListener = roomEventListener;
    this.myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
  }

  FirebaseUtils.RoomCallback roomCallback = new FirebaseUtils.RoomCallback() {
    @Override
    public void getRoomDataCallback(Room room) {
      if (room != null) {
        if (roomInfo == null || !roomInfo.isEqualsRoomBasicData(room))
          getRoomData(room);


        if(checkRoomAllPlayer(room.getPlayerState()))
          readyGame(room.getGame(), room.getPlayerState());

        if(checkReadyAllPlayer(room.getPlayerState())) {
          String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", null);
          if (roomId != null) {
            FirebaseUtils.updateTargetRoomPlayerState(roomId, myUid, Room.STATE.GAME);
            roomEventListener.startGame(room.getGame());
          }
        }

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

  private void getRoomData(Room room) {
    List<Player> list = new ArrayList<>();
    for (Iterator<String> it = room.getPlayerList().keySet().iterator(); it.hasNext(); ) {
      String key = it.next();
      Player player = new Player(room.getPlayerList().get(key));
      player.setUid(key);
      list.add(player);
    }
    masterName = room.getPlayerList().get(room.getMasterUID()).getName();
    masterUid = room.getMasterUID();
    playerList = new ArrayList<>(list);
    playerCount = playerList.size();
    roomEventListener.updateRoomData(masterUid, masterName, playerCount, playerList);
  }

  public void removeRoomManager() {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", null);
    if(roomId != null) {
      if (roomInfo != null) {
        if (uid.equals(roomInfo.getMasterUID())) {
          FirebaseUtils.removeTargetRoom(roomId);
        } else {
          FirebaseUtils.exitTargetRoom(roomId, uid);
        }
      }
      else {
        FirebaseUtils.exitTargetRoom(roomId, uid);
      }
      OtherUtils.removeSharedPreferenceData(context, "roomId");
    }
    removeDatabaseReferenceEventListener();
  }

  public void removeDatabaseReferenceEventListener() {
    if (roomDatabaseReference != null && roomValueEventListener != null)
      roomDatabaseReference.removeEventListener(roomValueEventListener);
  }


  private void readyGame(Room.GAME game, HashMap<String, Room.STATE> playerState) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", null);
    if (roomId != null) {
      if (playerState != null) {
        if (Room.GAME.NOT != game) {
          if (checkPlayerSelfState(playerState)) {
            FirebaseUtils.updateTargetRoomPlayerState(roomId, uid, Room.STATE.READY);
          }
        }
      }
    }
  }


  private boolean checkPlayingGame(HashMap<String, Room.STATE> playerState) {
    for (Room.STATE state : playerState.values()) {
      if (state != Room.STATE.GAME || state == Room.STATE.FINISH) {
        return true;
      }
    }
    return false;
  }

  private boolean checkReadyAllPlayer(HashMap<String, Room.STATE> playerState) {
    for (Room.STATE state : playerState.values()) {
      if (state != Room.STATE.READY) {
        return false;
      }
    }
    return true;
  }

  private boolean checkRoomAllPlayer(HashMap<String, Room.STATE> playerState) {
    for (Room.STATE state : playerState.values()) {
      if (state != Room.STATE.ROOM) {
        return false;
      }
    }
    return true;
  }

  // ROOM일때 False 반환 => 방에 입장 후거나 게임 종료 후 방으로 나온 경우
  // 상태가 GAME 이거나 FINISH 이면 TRUE 반환 => 게임 중이거나 결과 화면
  private boolean checkPlayerSelfState(HashMap<String, Room.STATE> playerState) {
      String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
      if (playerState.get(uid) == Room.STATE.READY ||
          playerState.get(uid) == Room.STATE.GAME ||
          playerState.get(uid) == Room.STATE.FINISH) {
        return false;
      }
    return true;
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
