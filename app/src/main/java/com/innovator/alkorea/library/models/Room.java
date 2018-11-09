package com.innovator.alkorea.library.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Room implements Parcelable {

  public enum STATE {
    ROOM, READY, GAME, FINISH
  }

  public enum GAME {
    NOT, SEQUENCE, TAP
  }

  private String masterUID;
  private GAME game;
  private HashMap<String, Player> playerList;
  private HashMap<String, STATE> playerState;
  private HashMap<String, Integer> playerScore;

  public Room() {

  }

  public Room(String masterUID) {
    this.masterUID = masterUID;
  }

  public Room(String masterUID, GAME game) {
    this.masterUID = masterUID;
    this.game = game;
  }

  public Room(String masterUID, HashMap<String, Player> playerList) {
    this.masterUID = masterUID;
    this.playerList = playerList;
  }

  public Room(String masterUID, GAME game, HashMap<String, Player> playerList) {
    this.masterUID = masterUID;
    this.game = game;
    this.playerList = playerList;
  }

  public Room(String masterUID, GAME game, HashMap<String, Player> playerList, HashMap<String, STATE> playerState) {
    this.masterUID = masterUID;
    this.game = game;
    this.playerList = playerList;
    this.playerState = playerState;
  }

  public Room(String masterUID, GAME game, HashMap<String, Player> playerList, HashMap<String, STATE> playerState, HashMap<String, Integer> playerScore) {
    this.masterUID = masterUID;
    this.game = game;
    this.playerList = playerList;
    this.playerState = playerState;
    this.playerScore = playerScore;
  }

  public Room(Parcel parcel) {
    this.masterUID = parcel.readString();
    int game = parcel.readInt();
    this.game = GAME.values()[game];
    this.playerList = parcel.readHashMap(Player.class.getClassLoader());
    this.playerState = parcel.readHashMap(STATE.class.getClassLoader());
  }

  public HashMap<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("masterUID", masterUID);
    result.put("game", game);
    result.put("playerList", playerList);
    result.put("playerState", playerState);
    return result;
  }

  public boolean isEqualsRoomBasicData(Room room) {
    return (masterUID.equals(room.getMasterUID()) && playerList.equals(room.getPlayerList()));
  }

  public String getMasterUID() {
    return masterUID;
  }

  public void setMasterUID(String masterUID) {
    this.masterUID = masterUID;
  }


  public GAME getGame() {
    return game;
  }

  public void setGame(GAME game) {
    this.game = game;
  }

  public HashMap<String, Player> getPlayerList() {
    return playerList;
  }

  public void setPlayerList(HashMap<String, Player> players) {
    this.playerList = players;
  }

  public HashMap<String, STATE> getPlayerState() {
    return playerState;
  }

  public void setPlayerState(HashMap<String, STATE> playerState) {
    this.playerState = playerState;
  }

  public HashMap<String, Integer> getPlayerScore() {
    return playerScore;
  }

  public void setPlayerScore(HashMap<String, Integer> playerScore) {
    this.playerScore = playerScore;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(masterUID);
    parcel.writeInt(game.ordinal());
    parcel.writeMap(playerList);
    parcel.writeMap(playerState);
  }

  public static final Creator<Room> CREATOR = new Creator<Room>() {
    @Override
    public Room createFromParcel(Parcel in) {
      return new Room(in);
    }

    @Override
    public Room[] newArray(int size) {
      return new Room[size];
    }
  };
}
