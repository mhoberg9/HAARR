package de.tuberlin.sese.swtpp.gameserver.model.cannon;

import java.util.LinkedList;

public class Field {
	// private boolean empty = false;
	// boolean white = true;
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
		// if (c == 'b' || c == 'B')
		// this.color = "b";
		// if (c == 'B')
		// this.castle = true;
		// if (c == 'w' || c == 'W')
		// this.color = "w";
		// if (c == 'W')
		// this.castle = true;
		if (c == '1') {
			this.color = null;

		}
		this.column = column;
		this.row = row;
		this.postion = "" + column + "" + row;

	}

	public String getPostion() {
		return postion;
	}



	@Override
	public String toString() {
		// if (color.equals("w") && castle == false)
		// return "w";
		// if (color.equals("w") && castle == true)
		// return "W";
		// if (color.equals("b") == false && castle == false)
		// return "b";
		// if (color.equals("b") && castle == true) {
		// return "B";
		// }
		if (color != null)
			return color;
		return "1";

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
