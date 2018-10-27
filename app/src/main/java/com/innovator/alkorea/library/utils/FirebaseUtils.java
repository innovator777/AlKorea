package com.innovator.alkorea.library.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;

import java.util.HashMap;
import java.util.Map;

//Create innovator(JongChan Yang)
public class FirebaseUtils {

  public interface RoomCallback {
    void getRoomDataCallback(Room room);
    void cancelledCallback(DatabaseError error);
  }

  private static String TAG = FirebaseUtils.class.getName();
  private static final String baseKey = "room";
  private static final String playerKey = "players";
  private static final String gameKey = "game";
  private static final String playerStateKey = "playerState";
  private static final String playerScore = "playerScore";

  public static FirebaseDatabase getDatabase(){
    return FirebaseDatabase.getInstance();
  }

  public static DatabaseReference getDBRootReference() {
    return FirebaseDatabase.getInstance().getReference();
  }

  public static DatabaseReference getDBBaseRerference() {
    return getDBRootReference().child(baseKey);
  }

  public static DatabaseReference getDBTargetRoomIDReference(String roomId) {
    return getDBBaseRerference().child(roomId);
  }

  public static DatabaseReference getDBTargetRoomPlayerReference(String roomId) {
    return getDBTargetRoomIDReference(roomId).child(playerKey);
  }

  public static DatabaseReference getDBTargetRoomGameReference(String roomId) {
    return getDBTargetRoomIDReference(roomId).child(gameKey);
  }

  public static DatabaseReference getDBTargetRoomPlayerScore(String roomId) {
    return getDBTargetRoomIDReference(roomId).child(playerScore);
  }

  public static DatabaseReference getDBTargetRoomPlayerStateReference(String roomId) {
    return getDBTargetRoomIDReference(roomId).child(playerStateKey);
  }

  public static void addRoomData(String roomId, Room room) {
    Map<String, Object> roomData = room.toMap();
    getDBBaseRerference().child(roomId).updateChildren(roomData);
  }


  public static void admitTargetRoom(String roomId, String uid, Player player) {
    Map<String, Object> playerData = player.toMap();
    getDBTargetRoomPlayerReference(roomId).child(uid).updateChildren(playerData);
  }

  public static void exitTargetRoom(String roomId, String uid) {
    String key = getDBTargetRoomPlayerReference(roomId).getKey();
    if (!key.isEmpty() || key != null)
      getDBTargetRoomPlayerReference(roomId).child(uid).setValue(null);
  }

  public static void removeTargetRoom(String roomId) {
    String key = getDBTargetRoomIDReference(roomId).getKey();
    if (!key.isEmpty() || key != null)
      getDBTargetRoomIDReference(roomId).setValue(null);
  }

  public static void updateTargetRoomPlayerState(String roomId, String uid, Room.STATE state) {
    getDBTargetRoomPlayerStateReference(roomId).child(uid).setValue(state);
  }

  public static void updateTargetRoomPlayerScore(String roomId, String uid, int score){
    getDBTargetRoomPlayerScore(roomId).child(uid).setValue(score);
  }

  public static void updateTargetRoomGame(String roomId, Room.GAME game) {
    Map<String, Object> map = new HashMap<>();
    map.put(gameKey, game);
    getDBTargetRoomIDReference(roomId).updateChildren(map);
  }

  public static ValueEventListener getDBTargetRoomDataWithListener(String roomId, DatabaseReference databaseReference, final RoomCallback roomCallback) {
    return databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Room room = dataSnapshot.getValue(Room.class);
        roomCallback.getRoomDataCallback(room);
      }
      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        roomCallback.cancelledCallback(databaseError);
      }
    });
  }

  public static String createRoomIDAutoKey() {
    return getDBBaseRerference().push().getKey();
  }
}
