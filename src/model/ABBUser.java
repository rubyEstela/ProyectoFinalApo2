package model;

@SuppressWarnings("serial")
public class ABBUser implements Serializable  {

	private User root;
	public ABBUser() {
		root = null;
	}
	/**
	 * Metodo que agrega un nuevo usuario al arbol binario de busqueda si la raiz es null
	 * en caso contrario llama al metodo recursivo de la clase Usuario insertar(Usuario)  
	 * @param actual != null
	 * @param nuevo != null, nuevo metodo a agregar
	 * @throws UsuarioRepetidoException Si en el metodo insertar(Usuario) de la clase Usuario se encuentra
	 * un usuario registrado con el mismo nombre que el de nuevo.getNombre()
	 */
	
	public void add(User actual, User nuevo) throws UsuarioRepetidoException {
		if(root==null) {
			root=nuevo;
		}else {
			root.insert(nuevo);
		}
	}
	/**
	 * Metodo recursivo que busca un usuario en el arbol binario de busqueda 
	 * @param actual !=null, usuario actual del recorridodel arbol
	 * @param nombre nombre del usuario que desea buscar
	 * @return El usuario buscado
	 * @throws UsuarioNoExisteException si no se encuentra ningun usuario con el nombre ingresado por parametro
	 */
	public User search(User actual, String nombre) throws UsuarioNoExisteException {
		if(actual.getName()==nombre) {
			return actual;
		}else {
			if(name.compareToIgnoreCase(actual.getNombre())<1) {
				if(actual.getIzquierdo()!=null) {
					return search(actual.getIzquierdo(), nombre);
				}else {
					throw new UsuarioNoExisteException(nombre);
				}
			}else {
				if(actual.getRight()!=null) {
					return search(actual.getRight(), nombre);
				}else {
					throw new UsuarioNoExisteException(nombre);
				}
			}
		}
	}
	/**
	 * @return the raiz
	 */
	public User getRaiz() {
		return root;
	}

	/**
	 * @param raiz the raiz to set
	 */
	public void setRoot(User root) {
		this.root = root;
	}
	/**
	 * Metodo que llama al metodo recursivo eliminar(String) de la clase usuario
	 * @param nombre != null
	 */
	public void remove (String name) {
		root=root.remove(name);
	}
}
