package com.marina.spots.dto;


import com.marina.spots.Dot;
import com.marina.spots.GameField;

import java.util.List;
import java.util.Queue;

public class OutputMessage extends DotDTO {

	private boolean isFree;
	private GameField gameField;
	private List<Queue<Dot>> allCyclesToDraw;
	private int scorePlayer2;
	private int scorePlayer1;
	private boolean finish;

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

	public void setScorePlayer2(int scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}

	public void setScorePlayer1(int scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public int getScorePlayer2() {
		return scorePlayer2;
	}

	public int getScorePlayer1() {
		return scorePlayer1;
	}

	public boolean isFinish() {
		return finish;
	}
}
