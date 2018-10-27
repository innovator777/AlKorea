package com.innovator.alkorea.library.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

import javax.xml.parsers.SAXParser;

public class Room implements Parcelable {

  public enum STATE {
    ROOM, GAME, FINISH
  }

  public enum GAME {
    NOT, SEQUENCE
  }

  private String masterUID;
  private GAME game;
  private HashMap<String, Player> players;
  private HashMap<String, STATE> playerState;
  private HashMap<String, Integer> score;

  public Room() {

  }

  public Room(String masterUID) {
    this.masterUID = masterUID;
  }

  public Room(String masterUID, GAME game) {
    this.masterUID = masterUID;
    this.game = game;
  }

  public Room(String masterUID, HashMap<String, Player> players) {
    this.masterUID = masterUID;
    this.players = players;
  }

  public Room(String masterUID, GAME game, HashMap<String, Player> players) {
    this.masterUID = masterUID;
    this.game = game;
    this.players = players;
  }

  public Room(String masterUID, GAME game, HashMap<String, Player> players, HashMap<String, STATE> playerState) {
    this.masterUID = masterUID;
    this.game = game;
    this.players = players;
    this.playerState = playerState;
  }

  public Room(String masterUID, GAME game, HashMap<String, Player> players, HashMap<String, STATE> playerState, HashMap<String, Integer> score) {
    this.masterUID = masterUID;
    this.game = game;
    this.players = players;
    this.playerState = playerState;
    this.score = score;
  }

  public Room(Parcel parcel) {
    this.masterUID = parcel.readString();
    int game = parcel.readInt();
    this.game = GAME.values()[game];
    this.players = parcel.readHashMap(Player.class.getClassLoader());
    this.playerState = parcel.readHashMap(STATE.class.getClassLoader());
//    int state = parcel.readInt();
//    this.state = STATE.values()[state];
  }

  public HashMap<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("masterUID", masterUID);
    result.put("game", game);
    result.put("players", players);
    result.put("playerState", playerState);
    return result;
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

  public HashMap<String, Player> getPlayers() {
    return players;
  }

  public void setPlayers(HashMap<String, Player> players) {
    this.players = players;
  }

  public HashMap<String, STATE> getPlayerState() {
    return playerState;
  }

  public void setPlayerState(HashMap<String, STATE> playerState) {
    this.playerState = playerState;
  }

  public HashMap<String, Integer> getScore() {
    return score;
  }

  public void setScore(HashMap<String, Integer> score) {
    this.score = score;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(masterUID);
//    parcel.writeInt(state.ordinal());
    parcel.writeInt(game.ordinal());
    parcel.writeMap(players);
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
