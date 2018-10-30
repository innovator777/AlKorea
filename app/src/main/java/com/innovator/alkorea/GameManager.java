package com.innovator.alkorea;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;

import java.util.HashMap;

public class GameManager {

  private final String TAG = GameManager.class.getName();

  private Context context;

  private DatabaseReference databaseReference;
  private ValueEventListener valueEventListener;
  private GameEventListener eventListener;

  public interface GameEventListener {
    void showGameResult(HashMap<String, Player> playerList, HashMap<String, Integer> resultList);
    void endGame();
  }

  public GameManager() {

  }

  public GameManager(Context context, GameEventListener eventListener) {
    this.context = context;
    String roomId = OtherUtils.getSharedPreferencesStringData(context, "roomId", "");
    if (!roomId.isEmpty()) {
      databaseReference = FirebaseUtils.getDBTargetRoomIDReference(roomId);
      valueEventListener = FirebaseUtils.getDBTargetRoomDataWithListener(roomId, databaseReference, gameCallback);
    }
    this.eventListener = eventListener;
  }

  FirebaseUtils.RoomCallback gameCallback = new FirebaseUtils.RoomCallback() {
    @Override
    public void getRoomDataCallback(Room room) {
      if (room != null) {
        if (room.getGame() != Room.GAME.NOT) {
          for (String key : room.getPlayerState().keySet()) {
            Room.STATE state = room.getPlayerState().get(key);
            Log.i(TAG, state.toString());
            if (state == Room.STATE.GAME) {
              return;
            }
          }
          eventListener.showGameResult(room.getPlayerList(), room.getPlayerScore());
        }
        else {
          eventListener.endGame();
        }
      }
      else {
        removeDatabaseReferenceEventListener();
      }
    }

    @Override
    public void cancelledCallback(DatabaseError error) {
      Log.i(TAG, error.getMessage());
    }
  };

  public void removeDatabaseReferenceEventListener() {
    if (databaseReference != null && valueEventListener != null)
      databaseReference.removeEventListener(valueEventListener);
  }

}
