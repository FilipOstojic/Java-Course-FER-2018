package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Razred <code>EmptyStackException</code> predstavlja iznimku koja se poziva
 * kada kada je stog (neočekivano) prazan ili ne sadrži dani ključ.
 * 
 * @author Filip
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            opis iznimke
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Konstruktor bez argumenata.
	 */
	public EmptyStackException() {
		super("The stack is empty.");
	}
}
