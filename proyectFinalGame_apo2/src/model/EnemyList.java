package model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class EnemyList implements Serializable{

	private EnemyList head;
	
	private int size;
	
	
	public EnemyList() {
		head=null;
		size=0;
	}
	
	/**
	 * @return the cabeza
	 */
	public EnemyList getHead() {
		return head;
	}

	/**
	 * @param cabeza the cabeza to set
	 */
	public void setHead(EnemyList head) {
		this.head = head;
	}

	/**
	 * @return the size
	 */
	public int size() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Metodo que agrega un nuevo enemigo a la lista de enemigos 
	 * @param nuevo != null
	 */
	public void add(EnemyList nuevo) {
		if(head==null) {
			head=nuevo;
		}else {
			EnemyList temp=head;
			while(temp.getSiguiente()!=null) {
				temp=temp.getSiguiente();
			}
			temp.setSiguiente(nuevo);
		}
		setSize(size() + 1);
	}
	
	/**
	 * Metodo que limpia la lista de enemigos, haciendo que la cabeza pierda las referencias de sus siguentes
	 * y luego hace el valor de la cabeza=null
	 */
	public void clear() {
		head.setSiguiente(null);
		head=null;
		setSize(0);
	}
	
	/**
	 * Metodo que devuelve un Enemigo dado por el parametro index
	 * @param index != null
	 * @return El enemigo en la posicion del parametro index
	 */
	public EnemyList get(int index) {
		int c=0;
		EnemyList temp=head;
		while(c<index) {
			if(temp.getSiguiente()!=null) {
				temp=temp.getSiguiente();
			}
			c++;
		}
		return temp;
	}
	
}
