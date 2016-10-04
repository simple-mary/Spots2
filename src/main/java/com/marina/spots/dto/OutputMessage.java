package com.marina.spots.dto;


import com.marina.spots.Dot;
import com.marina.spots.GameField;

import java.util.List;
import java.util.Queue;

public class OutputMessage extends DotDTO {

	private boolean isFree;
	private GameField gameField;
	List<Queue<Dot>> allCyclesToDraw;

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean free) {
		isFree = free;
	}

	public GameField getGameField() {
		return gameField;
	}

	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

	public List<Queue<Dot>> getAllCyclesToDraw() {
		return allCyclesToDraw;
	}

	public void setAllCyclesToDraw(List<Queue<Dot>> allCyclesToDraw) {
		this.allCyclesToDraw = allCyclesToDraw;
	}
}
