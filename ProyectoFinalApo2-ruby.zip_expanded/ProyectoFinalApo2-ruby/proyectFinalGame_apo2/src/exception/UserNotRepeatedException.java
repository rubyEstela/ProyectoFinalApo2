package exception;


@SuppressWarnings("serial")
public class UserNotRepeatedException extends Exception {

	/**
	 * Constructor de la exception UsuarioNoExisteException
	 * @param nombre != null, nombre del usuario que no logro encontrarse en el ABB de usuarios
	 */
	public UserNotRepeatedException(String name) {
		super("No pudimos encontrar ningun usuario con el nombre especificado: "+name);
	}
	
}
