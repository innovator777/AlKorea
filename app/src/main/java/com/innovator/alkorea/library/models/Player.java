package com.innovator.alkorea.library.models;

import java.util.HashMap;

public class Player {

  private String name;
  private int sex;
  private int number;

  public Player() {

  }

  public Player(Player player){
    this.name = player.getName();
    this.sex = player.getSex();
    this.number = player.getNumber();
  }

  public Player(String name, int sex) {
    this.name = name;
    this.sex = sex;
  }

  public Player(String name, int sex, int number) {
    this.name = name;
    this.sex = sex;
    this.number = number;
  }

  public HashMap<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("name", name);
    result.put("sex", sex);
    result.put("number", number);
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

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}
