package exception;


@SuppressWarnings("serial")
public class UserRepeatedException extends Exception {

	/**
	 * Constructor de la Excepcion UsuarioRepetidoException
	 * @param nombre != null, nombre del usuario existente
	 */
	public UserRepeatedException(String name) {
		super("Ya existe un usuario con el nombre especificado: "+name);
	}
	
}
