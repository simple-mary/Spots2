package com.marina.spots.dto;


import java.util.Date;

public class OutputMessage extends DotDTO {

	private Date time;
	
	public OutputMessage(DotDTO original, Date time) {
		super(original.getX(), original.getY(), original.getDotValues());
		this.time = time;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
}
