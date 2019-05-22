package model;
import java.io.Serializable;

@SuppressWarnings("serial")
public class EnemyList implements Serializable {

	private Enemy head;
	private int size;
	
	/**
	 * Constructor de la clase lista enemigo
	 */
	public EnemyList() {
		head=null;
		size=0;
	}
	/**
	 * @return head
	 */
	public Enemy getHead() {
		return head;
	}

}
