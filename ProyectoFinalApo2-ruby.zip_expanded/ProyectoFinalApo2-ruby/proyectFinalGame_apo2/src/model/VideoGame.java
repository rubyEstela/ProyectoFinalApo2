package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import exception.ArbolVacioException;
import exception.PuntajeNoExiste;
import exception.UserRepeatedException;


@SuppressWarnings("serial")
public class VideoGame implements Serializable{

	public static final int LONG_WINDOW=360;
	public static final int WHIDTH_WINDOW=621;
	
	private User usuario;
	private ABBUser arbolUsuarios;
	
	private EnemyBoss eB;
	
	private EnemyList  listaEnemigos;
	
	/**
	 *buider of the class Game
	 */
	public VideoGame() {		
		loadEnemy();
	    loadBoss();
	}
	
	/**
	 * Method that loads the users of a serialized file
	 * @param name != null
	 * @throws UserRepeatedException If you try to add the user, find an existing user with the same name
	 */
	public void LoadUser(String name) throws UserRepeatedException{
		try {
			ObjectInputStream cargarUsuarios=new ObjectInputStream(new FileInputStream("src/usuarios/arbol.dat"));
			arbolUsuarios=(ABBUser)cargarUsuarios.readObject();
			cargarUsuarios.close();
		}catch(Exception e) {
			arbolUsuarios=new ABBUser();
		}finally {
			usuario= new User(name);
			arbolUsuarios.add(arbolUsuarios.getRoot(), usuario);
		}
	}
	
	/**
	 * Method that serializes the binary tree of the users' ship
	 */
	public void saveUser() {
		try {
			ObjectOutputStream salvar=new ObjectOutputStream(new FileOutputStream("src/usuarios/arbol.dat"));
			salvar.writeObject(arbolUsuarios);
			salvar.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
    Method that loads enemies from a serialized file	 */
	public void loadEnemy() {
		try {
			ObjectInputStream cargar=new ObjectInputStream(new FileInputStream("src/save/NormalEnemies.dat"));
			listaEnemigos=(EnemyList) cargar.readObject();
			cargar.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     *Method that reads a text file and instantiates a new EnemigoBoss with the
	 * values ​​obtained from the text file
	 */
	public void LoadBoss() {
		try {
			BufferedReader lector=new BufferedReader(new FileReader("src/save/Boss.txt"));
			String linea=lector.readLine();
			String[] str=linea.split(",");
			eB=new EnemyBoss(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), str[3], Boolean.parseBoolean(str[4]), str[5].charAt(0) );
			lector.close();
		} catch (Exception e) {

		}
	}
	
	/**
     *Method that seeks the highest score of all users	 * @return El mayor puntaje de entre todos los usuarios
	 * @throws ArbolVacioException Si se intenta llamar este metodo cuando el arbol esta vacio
	 */
	public int SearchHigherScore() throws ArbolVacioException {
		int mayor=0;
		ArrayList<User> usuarios=arrayUser();
		if(usuarios.size()==0) {
			throw new ArbolVacioException();
		}else {
			for(int i=1;i<usuarios.size();i++) {
				User porInsertar=(User)usuarios.get(i);
				boolean termino=false;
				for(int j=i;j>0 && !termino;j--) {
					User actual=(User)usuarios.get(j-1);
					if(actual.compareTo(porInsertar)>0) {
						usuarios.set(j, actual);
						usuarios.set(j-1, porInsertar);
					}else {
						termino=true;
					}
				}
			}
			mayor=usuarios.get(0).getPoint();
			return mayor;
		}
	}
	
	/**
	 * Metodo que ordena un arreglo conforme a los nombres de los objetos en el 
	 * @return ArrayList ordenado conforme a los nombres de los usuarios
	 */
	public ArrayList<User> ordenName(){
		ArrayList<User> usuarios= arrayUser();
		ComparatorName cN=new ComparatorName();
		for(int i=1;i<usuarios.size();i++) {
			for(int j=i;j>0 && cN.compare(usuarios.get(j-1), usuarios.get(j))>0;j--) {
				User temp=usuarios.get(j);
				usuarios.set(j, usuarios.get(j-1));
				usuarios.set(j-1, temp);
			}
		}
		return usuarios;
	}
	
	/**
	 * Metodo que ordena un arreglo conforme a los puntos de los objetos en el 
	 * @return ArrayList ordenado conforme a los puntos de los usuarios
	 */
	public ArrayList<User> ordenPoint() {
		ArrayList<User> usuarios=arrayUser();
		for(int i=1;i<usuarios.size();i++) {
			User porInsertar=(User)usuarios.get(i);
			boolean termino=false;
			for(int j=i;j>0 && !termino;j--) {
				User actual=(User)usuarios.get(j-1);
				if(actual.compareTo(porInsertar)>0) {
					usuarios.set(j, actual);
					usuarios.set(j-1, porInsertar);
				}else {
					termino=true;
				}
			}
		}
		return usuarios;
	}
	
	/**
	 * Metodo que toma un arreglo y lo convierte a un solo objeto de tipo String 
	 * @param arreglo != null
	 * @return String que contiene todos los toString() de los objetos en el parametro arreglo
	 */
	public String arrayToString(ArrayList<User> arreglo) {
		String end="";
		for(int m=0;m<arreglo.size();m++) {
			int j=m+1;
			end+=""+j+")"+arreglo.get(m).toString()+" ";
			end+="\n";
		}
		return end;
	}
	
	/**
	 * Metodo recursivo que toma un arreglo y lo convierte en un solo objeto de tipo String
	 * @param arreglo != null, El arreglo a convertir 
	 * @param i != null, posicion del arreglo a tomar
	 * @param j != null, contador
	 * @param end != null, acomulador de String 
	 * @return objeto de tipo String con todos los toString() de los objetos del arreglo separados por "\n" 
	 */
	public String arrayToString(ArrayList<User> arreglo, int i, int j, String end) {
		if(i<arreglo.size()) {
			j=i+1;
			end+=""+j+")"+arreglo.get(i).toString()+" ";
			end+="\n";
			return arrayToString(arreglo, i++, j, end);
		}else {
			return end;
		}
	}
	
	/**
	 * Metodo que busca en un arreglo ordenado un usuario con un puntaje igual al parametro valor
	 * @param valor != null, Parametro con el puntje a buscar 
	 * @return el toString() del usuario encontrado con el puntaje especificado
	 * @throws PuntajeNoExiste si en el arreglo no hay ningun usuario con u puntaje igul el parametro especificad
	 */
	public String searchBinary (int valor) throws PuntajeNoExiste{
		boolean encontre=false;
		int inicio=0;
		int medio=0;
		ArrayList<User> usuarios=ordenPoint();
		int fin=usuarios.size()-1;
		while(inicio<=fin && !encontre){
				medio=(inicio+fin)/2;
			if(usuarios.get(medio).getPoint()==valor){
				encontre=true;
			}else if(usuarios.get(medio).getPoint()<valor){
				fin=medio-1;
			}else{
				inicio=medio+1;
			}
		}if(encontre==false) {
			throw new PuntajeNoExiste(valor); 
		}else {
			return usuarios.get(medio).toString();
		}
	}
	
	/**
	 * Metodo que convierte un arbol binario de busqueda a un ArrayList de tipo usuario
	 * @return Arraylist de tipo usuario con el contenido del arbol binario de busqueda 
	 */
	public ArrayList<User> arrayUsuarios(){
		ArrayList<User> nombres = new ArrayList<User>();
		toArray(getArbolUser().getRaiz(), nombres);
		return nombres;
	}
	
	/**
	 * Metodo recursivo que recorre el arbol binario de busqueda y agrega cada elemento a un ArrayList de tipo Usuario 
	 * @param actual Usuario actual en el que va el recorrido del arbol binario de busqueda
	 * @param nombres != null 
	 */
	public void toArray(User actual, ArrayList<User> nombres){
		if(actual!=null ) {
			if(actual.isHoja()) {
				nombres.add(actual);
			}else {
				nombres.add(actual);
				toArray(actual.getIzquierdo(), nombres);
				toArray(actual.getDerecho(), nombres);
			}
		}
	}

	/**
	 * @return the usuario
	 */
	public User getUser() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the arbolUsuarios
	 */
	public ABBUser getArbolUser() {
		return arbolUsuarios;
	}

	/**
	 * @param arbolUsuarios the arbolUsuarios to set
	 */
	public void setArbolUsuarios(ABBUser arbolUsuarios) {
		this.arbolUsuarios = arbolUsuarios;
	}

	/**
	 * @return the eB
	 */
	public EnemyBoss geteB() {
		return eB;
	}

	/**
	 * @param eB the eB to set
	 */
	public void seteB(EnemyBoss eB) {
		this.eB = eB;
	}

	/**
	 * @return the listaEnemigos
	 */
	public EnemyList  getListaEnemigos() {
		return listaEnemigos;
	}

	/**
	 * @param listaEnemigos the listaEnemigos to set
	 */
	public void setListaEnemigos(EnemyList listaEnemigos) {
		this.listaEnemigos = listaEnemigos;
	}
	
	
	
	
}