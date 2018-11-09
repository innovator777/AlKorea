package com.innovator.alkorea.library.models;


public class GameItem {

  private int icon;
  private String name;
  private Room.GAME game;

  public GameItem(int icon, String name, Room.GAME game) {
    this.icon = icon;
    this.name = name;
    this.game = game;
  }

  public int getIcon() {
    return icon;
  }

  public void setIcon(int icon) {
    this.icon = icon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Room.GAME getGame() {
    return game;
  }

  public void setGame(Room.GAME game) {
    this.game = game;
  }

}
