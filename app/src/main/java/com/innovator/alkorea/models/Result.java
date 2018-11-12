package com.innovator.alkorea.models;

public class Result {

  private String rank;
  private Player playerInfo;
  private String result;

  public Result() {

  }

  public Result(String rank, Player playerInfo, String result) {
    this.rank = rank;
    this.playerInfo = playerInfo;
    this.result = result;
  }


  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public Player getPlayerInfo() {
    return playerInfo;
  }

  public void setPlayerInfo(Player playerInfo) {
    this.playerInfo = playerInfo;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }


}
