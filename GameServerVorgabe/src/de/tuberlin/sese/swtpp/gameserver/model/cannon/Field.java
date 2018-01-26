package de.tuberlin.sese.swtpp.gameserver.model.cannon;

import java.util.LinkedList;

public class Field {

	String color;
	private final String postion;
	char column;
	int row;

	public Field(String color, char column, int row) {

		this.color = color;
		this.column = column;
		this.row = row;
		this.postion = "" + column + "" + row;
	}

	public Field(char column, int row, char c) {

	this.color=String.valueOf(c);
		this.column = column;
		this.row = row;
		this.postion = "" + column + "" + row;

	}

	public String getPostion() {
		return postion;
	}


@Override
	public String toString() {
		return color;

	}

	public boolean isSolider() {
		return !color.equals(color.toUpperCase());
	}

	public boolean isCastle() {
		return !color.equals(color.toLowerCase());
	}

	public boolean isEmpty() {
		return this.color == null;
	}

	public boolean isPlayer(String player) {
		return color.equalsIgnoreCase(player);
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public char getCol() {
		return this.postion.charAt(0);
	}

	public int getRo() {
		return this.postion.charAt(1);

	}

	public void destroy() {
		color="1";
	}
}
