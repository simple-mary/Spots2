package com.marina.spots.dto;

import com.marina.spots.Dot;

import java.util.Date;

public class OutputMessage extends Message {

	private Date time;
	
	public OutputMessage(Dot original, Date time) {
		super(original.getX(), original.getY());
		this.time = time;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
}
