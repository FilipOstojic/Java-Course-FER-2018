package hr.fer.zemris.java.hw07.shell;

/**
 * Razred <code>ShellIOException</code> predstavlja iznimku koja se poziva ako
 * se dogodi bilo kakva greška u Shell-u.
 * 
 * @author Filip Ostojić
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            opis iznimke
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Konstruktor bez argumenata.
	 */
	public ShellIOException() {
		super();
	}
}
