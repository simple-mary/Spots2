package com.marina.spots.dto;

import com.marina.spots.DotValues;

public class Message {

  private int x;
  private int y;
  private DotValues dotValues;

  public Message(int x, int y) {
    
  }
  
  public Message(int x, int y, DotValues dotValues) {
    this.x = x;
    this.y = y;
    this.dotValues = dotValues;
  }

//  public String getMessage() {
//    return message;
//  }
//
//  public void setMessage(String message) {
//    this.message = message;
//  }
//
//  public int getId() {
//    return id;
//  }
//
//  public void setId(int id) {
//    this.id = id;
//  }
}
