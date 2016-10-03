package com.marina.spots.dto;

import com.marina.spots.DotValues;

public class DotDTO {

  private int x;
  private int y;
  private DotValues dotValues;

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public DotValues getDotValues() {
    return dotValues;
  }

  public void setDotValues(DotValues dotValues) {
    this.dotValues = dotValues;
  }

  public DotDTO() {
    
  }
  
  public DotDTO(int x, int y, DotValues dotValues) {
    this.x = x;
    this.y = y;
    this.dotValues = dotValues;
  }


}
