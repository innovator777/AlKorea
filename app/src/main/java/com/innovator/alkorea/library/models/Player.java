package com.innovator.alkorea.library.models;

import java.util.HashMap;

public class Player {

  private String uid;
  private String name;
  private int sex;

  public Player() {

  }

  public Player(Player player){
    this.uid = player.getUid();
    this.name = player.getName();
    this.sex = player.getSex();
  }

  public Player(String uid, String name, int sex) {
    this.uid = uid;
    this.name = name;
    this.sex = sex;
  }

  public Player(String name, int sex) {
    this.name = name;
    this.sex = sex;
  }

  public HashMap<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("name", name);
    result.put("sex", sex);
    return result;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }
}
