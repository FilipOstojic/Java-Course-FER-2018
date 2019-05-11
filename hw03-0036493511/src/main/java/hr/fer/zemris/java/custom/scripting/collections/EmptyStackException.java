package hr.fer.zemris.java.custom.scripting.collections;
/**
 * Razred <code>EmptyStackException</code> predstavlja iznimku koja se poziva kada
 * kada je stog (neočekivano) prazan.
 * 
 * @author Filip Ostojić
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * @param message opis iznimke
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
