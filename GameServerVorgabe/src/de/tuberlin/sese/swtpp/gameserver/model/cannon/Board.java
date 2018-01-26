package de.tuberlin.sese.swtpp.gameserver.model.cannon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board implements Serializable {
	private static int counter = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1656131597159235160L;

	public static FieldHandler fieldList = new FieldHandler();

	/**
	 * @TODO
	 * @param posFields
	 *            InputList that will be checked for possibilities of retreat
	 * @param requestingPlayer
	 * @return
	 */
	// skaer skar
	public static boolean inDanger(List<String> posFields,
			String requestingPlayer) {

		List<String> tempList2 = posFields.subList(0, 8);
		List<Field> tempList = fieldList.stream()
				.filter(a -> tempList2.contains(a.getPostion()))
				.collect(Collectors.toList());
		return tempList.stream().anyMatch(a -> !a.isPlayer(requestingPlayer));
	}

	/**
	 * 
	 * @param posFields
	 *            Liste aus die durch Algorithmus ausgew�hlt wurden
	 * @return
	 */
	public List<String> retreatFields(List<String> posFields) {

		List<String> tempList = posFields.subList(5, 11);
		List<Field> tempList2 = fieldList.stream()
				.filter(a -> tempList.contains(a.getPostion()))
				.collect(Collectors.toList());

		List<String> posRetreat = new ArrayList<String>();

		if (tempList2.get(0).isEmpty() && tempList.get(3).isEmpty()) {
			posRetreat.add(tempList2.get(3).getPostion());
		} else if (tempList2.get(1).isEmpty() && tempList.get(4).isEmpty()) {
			posRetreat.add(tempList2.get(4).getPostion());
		} else if (tempList2.get(2).isEmpty() && tempList.get(5).isEmpty()) {
			posRetreat.add(tempList2.get(5).getPostion());
		} else {
			posRetreat.add("NO RETREAT");
		}
		return posRetreat;

	}

	public static boolean checkRetreat(String to,
			List<String> possibleRetreatFields, String requestingPlayer) {
		return possibleRetreatFields.contains(to)
				&& inDanger(possibleRetreatFields, requestingPlayer);
	}

	// �berpr�fung sieht immer wie folgt aus eigener Stein auf fromMove
	// Gegner
	// etc
	// auf toMove

	/**
	 * Bei normal Move checken ob findway teilliste von den ersten 3 von MARK
	 * ist �berpr�fung auch direkt durch move ohne findway, also to Move is
	 * teil von subliste(0,4) danach checken ob feld nicht durch eigenen spieler
	 * besetzt ist /**
	 * 
	 * @TODO check type safety for findway!!!
	 * 
	 * 
	 * @param fromMove
	 * @param toMove
	 * @param requestingPlayer
	 * @return
	 */

	public static List<String> isCannon(String fromMove, String toMove,
			String requestingPlayer) {
		List<String> moves = fieldList.findWay(fromMove, toMove);
		List<String> possibleMoves = fieldList.stream()
				.filter(a -> a.isPlayer(requestingPlayer))
				.map(Field::getPostion).collect(Collectors.toList());

		if (possibleMoves.containsAll(moves.subList(0, 3))
				&& fieldList.stream()
						.anyMatch(
								f -> f.getPostion().equals(moves.get(3))
										&& f.isEmpty()))
			return moves;
		else
			return Arrays.asList("FAILED!");

	}

	/**
	 * checks whether field is part of a canon if head is free and it's a move
	 * turn it swaps fields if head is free and it's a fire turn it destroys the
	 * field it fires at
	 * 
	 * 
	 * @TODO checken ob Gegner auf Feld ist!
	 * 
	 * @param fromMove
	 * @param toMove
	 * @param requestingPlayer
	 */
	public static boolean cannonAction(String fromMove, String toMove,
			String requestingPlayer) {
		List<String> moves = isCannon(fromMove, toMove, requestingPlayer);
		if (moves.size() == 4) {
			swap(moves.get(0), moves.get(1));
			return true;
		} else if (moves.size() > 4
				&& moves.size() < 7
				&& enemyAtPosition(moves.get(moves.size() - 1),
						requestingPlayer)) {
			destroy(moves.get(moves.size() - 1));
			return true;
		}
		return false;

	}

	/**
	 * 
	 * subliste(0,4) danach checken ob feld nicht durch eigenen spieler besetzt
	 * ist isPlayer hat 3-Seitige Logik-> wenn wir Gegnerischen spieler und
	 * nicht frei haben wollen m�ssen wir nicht player und isEmpty nehmen.
	 */

	public static boolean normalMoveCheck(String to,
			List<String> surroundingFields, String requestingPlayer) {
		return (surroundingFields.subList(0, 3).contains(to) && fieldList
				.stream().filter(f -> !f.isPlayer(requestingPlayer))
				.map(f -> f.getPostion()).anyMatch(f -> f.equals(to)));

	}

	/**
	 * TODO
	 */
	// Ist der eine Teil
	public boolean normalMove(String requestingPlayer,
			List<String> surroundingFields) {
		return fieldList.stream().anyMatch(
				a -> !a.isPlayer(requestingPlayer)
						&& surroundingFields.subList(0, 3).contains(
								a.getPostion()));

	}

	/**
	 * 
	 * @param to
	 *            Destination of move
	 * @param requestingPlayer
	 *            player whose turn it is
	 * @param surroundingFields
	 *            neighbor fields found by algorithm
	 * @return true if field from has neighbors and could move there
	 */
	public static boolean hasNeighborCheck(String to,
			List<String> surroundingFields, String requestingPlayer) {
		return hasNeighbor(surroundingFields, requestingPlayer).contains(to);
	}

	/**
	 * 
	 * @param surroundingFields
	 * @param requestingPlayer
	 * @return
	 */
	public static List<String> hasNeighbor(List<String> surroundingFields,
			String requestingPlayer) {
		List<String> neighbor = surroundingFields.subList(3, 5);
		if (fieldList.stream().anyMatch(
				a -> neighbor.contains(a.getPostion())
						&& neighbor.contains(!a.isPlayer(requestingPlayer)
								&& !a.isEmpty()))) {
			return fieldList
					.stream()
					.filter(a -> neighbor.contains(a.getPostion())
							&& neighbor.contains(!a.isPlayer(requestingPlayer)
									&& !a.isEmpty())).map(a -> a.getPostion())
					.collect(Collectors.toList());
		} else {
			return Arrays.asList("NO NEIGHBOR");
		}

	}

	public static String getBoard() {
		String s = "";

		for (int i = 0; i < fieldList.size(); i++) {
			//System.out.println("fieldget:"+"i"+" "+fieldList.get(i).getPostion()+" "+fieldList.get(i).getColor());
			s=s+(fieldList.get(i).toString());
		//	System.out.println("getBoardString: "+ s);

			if (((i + 1) % 10) == 0)
				s=s+"/";
		}
		s = s.substring(1, s.length() - 1);

		return s;
	}

	public static void swap(String fromM, String toM) {
		Field first = fieldList.stream()
				.filter(a -> a.getPostion().equals(fromM)).findFirst().get();
		Field last = fieldList.stream().filter(a -> a.getPostion().equals(toM))
				.findFirst().get();

		String tempColor = first.getColor();
		first.setColor(last.getColor());
		last.setColor(tempColor);
	}

	public static void destroy(String destination) {
		fieldList.stream().filter(a -> a.getPostion().equals(destination))
				.findFirst().get().destroy();
	}

	/**
	 * 
	 * @TODO !!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 *       algorithmus muss in anyMatch Stream geschehen und f�r jedes
	 *       Element, was zum spieler geh�rt anwenden
	 * 
	 *       1. Liste mit Feldern die nur vom Spieler bestzt sind 2. auf diese
	 *       Liste anymatch
	 * 
	 * 
	 *       finde ein Element, was vor sich frei hat, zum Spieler geh�rt und
	 *       nicht eine Burg ist
	 */

	public boolean canStillPlay(String requestingPlayer) {
		return fieldList.stream().anyMatch(
				a -> {
					List<String> posFields = FieldHandler.mark(a.getPostion(),
							requestingPlayer);
					if (inDanger(posFields, requestingPlayer)
							&& !retreatFields(posFields).contains("NO RETREAT")
							|| normalMove(requestingPlayer, posFields)
							|| !hasNeighbor(posFields, requestingPlayer)
									.contains("NO NEIGHBOR"))
						return true;
					return false;
				});
	}

	public List<Field> fieldsFromPositions(List<String> positions) {
		return fieldList.stream()
				.filter(f -> positions.contains(f.getPostion()))
				.collect(Collectors.toList());
	}

	public static void move(String from, String to) {
		String first = fieldList.stream()
				.filter(f -> f.getPostion().equals(from)).findFirst().get()
				.getColor();
		fieldList.stream().filter(f -> f.getPostion().equals(to)).findFirst()
				.get().setColor(first);
		fieldList.stream().filter(f -> f.getPostion().equals(from)).findFirst()
				.get().destroy();

	}

	public static boolean isFirstMove(String from, String to) {
		return counter < 2 && from.equals(to);
	}

	public static boolean setFirstMove(String position, String color) {
		System.out.println("Position: "+position);
	//	System.out.println(fieldList.size());
	//	fieldList.forEach(a->System.out.println(a.getPostion()));
	//	fieldList.subList(0, 20).stream().forEach(a->System.out.println("Sublist: "+a.toString()+" "+ a.getPostion()));
for(int r=0;r<fieldList.size();r++){
	System.out.println("Real: "+fieldList.get(r).getPostion()+" Color:"+fieldList.get(r).getColor());
}
		if((fieldList.subList(1, 9).stream().anyMatch(a->a.getPostion().equals(position))&&color.equalsIgnoreCase("w"))||
		
(fieldList.subList(91, 99).stream().anyMatch(a->a.getPostion().equals(position))&&color.equalsIgnoreCase("b"))){
	System.out.println("IF worked!");		
	Field castleField = fieldList.stream()
					.filter(f -> f.getPostion().equals(position)).findFirst()
					.get();
			int i = fieldList.indexOf(castleField);
			fieldList.get(i).setColor(color.toUpperCase());
			//System.out.println("TEST");
			//counter++;
			return true;
		}
		return false;
	}
/**
 * @TODO reparieren!!!!
 * 
 * @param move
 * @param requestingPlayer
 * @return
 */
	public static boolean performMove(String move, String requestingPlayer) {
		String from = move.split("-")[0];
		System.out.println(from);
		String to = move.split("-")[1];
		System.out.println(to);
		List<String> possibleFields = FieldHandler.mark(from, requestingPlayer);
		boolean fromIsOk = fieldList.stream().anyMatch(
				f -> f.isPlayer(requestingPlayer)
						&& f.getPostion().equals(from));
		boolean toIsOk = fieldList.stream()
				.anyMatch(
						f -> !f.isPlayer(requestingPlayer)
								&& f.getPostion().equals(to));
		
		if (isFirstMove(from, to)) {System.out.println("First Move");
			return setFirstMove(from, requestingPlayer);
		} 
		else if (fromIsOk && toIsOk) {
			if (cannonAction(from, to, requestingPlayer)) {
				return true;
			} else if (checkRetreat(to, possibleFields, requestingPlayer)
					|| normalMoveCheck(to, possibleFields, requestingPlayer)
					|| hasNeighborCheck(to, possibleFields, requestingPlayer)) {
				move(from, to);
				return true;
			}
		}
		return false;
	}

	public static boolean enemyAtPosition(String position,
			String requestingPlayer) {
		return fieldList.stream().anyMatch(
				f -> f.getPostion().equals(position)
						&& !f.isPlayer(requestingPlayer) && !f.isEmpty());
	}

	public static void setBoard(String state) {
		System.out.println(state);
		int b = 0;
		if (state.startsWith("/"))
			state = state.replaceFirst("/", "91/");
		if (state.endsWith("/"))
			state = state.substring(0, state.length() - 1).concat("/91");

		String[] splitted = state.split("/");
		int d=splitted.length-1;
int temp=splitted.length-1;
		for (int i = 0; i<splitted.length ;i++ ) {
		
			if (splitted[i].equals(""))
				splitted[i] = "91";

			splitted[i] = equalizeNotation(splitted[i]);
			

			for (char c : splitted[i].toCharArray()) {
				char column = (char) (97 + b);
				System.out.println(column+i);
				fieldList.add(new Field((column), d, c));
				//System.out.println(fieldList.get(i).getPostion()+"fieldList:"+fieldList.get(i).getColor());
				
				

				b++;
				
			}
			d--;
			b = 0;
		}
	

	}

	public static String equalizeNotation(String s) {
		s = s.replaceAll("2", "11");
		s = s.replaceAll("3", "111");
		s = s.replaceAll("4", "1111");
		s = s.replaceAll("5", "11111");
		s = s.replaceAll("6", "111111");
		s = s.replaceAll("7", "1111111");
		s = s.replaceAll("8", "11111111");
		s = s.replaceAll("9", "111111111");
		return s;
	}

}