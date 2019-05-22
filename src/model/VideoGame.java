package model;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class VideoGame implements Serializable {

	private User user;
	private Enemy enemys;
	private ListEnemy listEnemys;
	
	
	public VideoGame() {
		loadEnemys();
	}
	
	public void loadUser(String name) throws UsuarioRepetidoException {
		try {
			ObjectInputStream loadUser=new ObjectInputStream(new FileInputStream("src/usuarios/arbol.dat"));
			//arbolUsuarios=(ABBUsuario)cargarUsuarios.readObject();
			loadUser.close();
		}catch(Exception e) {
			//arbolUsuarios=new ABBUsuario();
		}finally {
			//user=new User(name);
			//arbolUsuarios.add(arbolUsuarios.getRaiz(), User);
		}
	}
	
	public void saveUser() {
		try {
			ObjectOutputStream save=new ObjectOutputStream(new FileOutputStream("src/usuarios/arbol.dat"));
			//save.writeObject(arbolUsuarios);
			save.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadEnemy() {
		try {
			ObjectInputStream load=new ObjectInputStream(new FileInputStream("src/save/NormalEnemies.dat"));
			listEnemys=(ListEnemy) load.readObject();
			load.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int buscarMayorPuntaje() throws ArbolVacioException {
		int mayor=0;
		ArrayList<User> usuarios=arrayUser();
		if(usuarios.size()==0) {
			throw new ArbolVacioException();
		}else {
			for(int i=1;i<user.size();i++) {
				User porInsertar=(User)user.get(i);
				boolean finish=false;
				for(int j=i;j>0 && !finish;j--) {
					User actual=(User)user.get(j-1);
					if(actual.compareTo(porInsertar)>0) {
						user.set(j, actual);
						user.set(j-1, porInsertar);
					}else {
						finish=true;
					}
				}
			}
			mayor=user.get(0).getPuntos();
			return mayor;
		}
	}
	
	public ArrayList<User> ordenNombres(){
		ArrayList<User> usuarios=arrayUser();
		ComparadorNombre cN=new ComparadorNombre();
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
	public ArrayList<User> ordenPuntos() {
		ArrayList<User> usuarios=arrayUsuarios();
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
	public String busquedaBinaria(int valor) throws PuntajeNoExiste{
		boolean encontre=false;
		int inicio=0;
		int medio=0;
		ArrayList<User> usuarios=ordenPuntos();
		int fin=usuarios.size()-1;
		while(inicio<=fin && !encontre){
				medio=(inicio+fin)/2;
			if(usuarios.get(medio).getPuntos()==valor){
				encontre=true;
			}else if(usuarios.get(medio).getPuntos()<valor){
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
		ArrayList<User> nombres = new ArrayList<Usuario>();
		toArray(getArbolUsuarios().getRaiz(), nombres);
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
		return user;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUser(User usuario) {
		this.user = usuario;
	}

	/**
	 * @return the arbolUsuarios
	 */
	public ABBUsuarios getArbolUser() {
		return arbolUsuarios;
	}

	/**
	 * @param arbolUsuarios the arbolUsuarios to set
	 */
	public void setArbolUser(ABBUsuario arbolUsuarios) {
		this.arbolUser = arbolUsuarios;
	}

	/**
	 * @return the eB
	 */
	public EnemigoBoss geteB() {
		return eB;
	}

	/**
	 * @param eB the eB to set
	 */
	public void seteB(EnemigoBoss eB) {
		this.eB = eB;
	}

	/**
	 * @return the listaEnemigos
	 */
	public Enemy getEnemyList() {
		return EnemyList;
	}

	/**
	 * @param listaEnemigos the listaEnemigos to set
	 */
	public void setEnemyList( Enemy listaEnemigos) {
		this.listEnemys  = listEnemys;
	}
	
	
	
}