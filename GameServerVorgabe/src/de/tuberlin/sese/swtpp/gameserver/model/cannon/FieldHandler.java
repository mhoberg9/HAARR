package de.tuberlin.sese.swtpp.gameserver.model.cannon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FieldHandler extends LinkedList<Field> {



	public static String getString(int a, int b, String position) {
		return "" + (char) a + position.charAt(0) + "" + (char) b + position.charAt(2);

	}

	// public List findWay(String move) {
	//
	// }
	public List<String> findWay(String move) {
		String fromTo[] = move.split("-");
		String from = fromTo[0];
		String to = fromTo[1];
		return findWay(from, to);
	}
/**
 * 
@TODO null muss geadded werden, wenn counter <6 ist!!!
 */
	public List<String> findWay(String from, String to) {

		int counter = 0;
		String way = from;
		ArrayList<String> fields = new ArrayList<String>();
		int fromCol = (int) from.charAt(0);
		int fromRow = (int) from.charAt(1);
		int toCol = (int) to.charAt(0);
		int toRow = (int) to.charAt(1);

	do	{	way = "" + (char) fromCol + "" + fromRow;
			fields.add(way);
			if (fromCol < toCol)
				fromCol++;
			if (fromCol > toCol)
				fromCol--;
			if (fromRow > toRow)
				toRow--;
			if (fromRow < toRow)
				toRow++;
			counter++;
		}while (fromCol != toCol && fromRow != toRow && counter <7);
	
		return fields;

	}

	/**
	 * This methods marks field surrounding a given field and inverts given matrix
	 * so that the output list can be used for further computation
	 * 
	 * @param field
	 *            that should be checked
	 * @param player
	 *            for who field should be checked. Can be either "b"- black or "w"-
	 *            white
	 * @return List of marked fields
	 */
	public static List<String> mark(String field, String player) {
		String from;
		String to;
		if (player.equalsIgnoreCase("w")) {
			from = getString(1, -1, field);
			to = getString(-1, 2, field);
		} else {
			from = getString(-1, 1, field);
			to = getString(1, -2, field);
		}

		return markFieldsAroundField(field, from, to);
	}

	/**
	 * 
	 * @param from
	 *            Start position
	 * @param to
	 *            Destination position
	 * @return List that contains all Strings but that surround a field or could be
	 *         possible turn
	 */
	private static List<String> markFieldsAroundField(String startField, String from, String to) {

		String field = from;
		List<String> ls = new ArrayList<String>();
		int fromCol = (int) from.charAt(0);
		int fromRow = (int) from.charAt(1);
		int toCol = (int) to.charAt(0);
		int toRow = (int) to.charAt(1);
		int temp = toCol;
		while (toRow != fromRow) {

			while (toCol != fromCol) {
				field = "" + (char) fromCol + "" + fromRow;
				if (!field.equals(startField))
					ls.add(field);
				if (toCol < fromCol)
					fromCol--;
				else
					fromCol++;
			}
			toCol = temp;
			if (fromRow < toRow)
				fromRow++;
			else
				fromRow--;
		}
		return ls;
	}
}
