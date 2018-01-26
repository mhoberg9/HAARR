package de.tuberlin.sese.swtpp.gameserver.model.cannon;

import java.util.LinkedList;

public class Cannon{

	Field head;
	Field center;
	Field tail;
	
	LinkedList<Field> figureholder;
	
	



	public Cannon(Field head, Field center, Field tail, LinkedList<Field> figureholder) {
		super();
		this.head = head;
		this.center = center;
		this.tail = tail;
		this.figureholder = figureholder;
	}





	public boolean canFire(String fireAt) {
		return figureholder.stream().anyMatch(a->a.getPostion().equals(fireAt))&& figureholder.removeFirst().isEmpty();
	}
	
}
