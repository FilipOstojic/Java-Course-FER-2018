package hr.fer.zemris.java.hw03.prob1;

/**
 * Razred <code>LexerException</code> predstavlja sve greške koje se mogu dobiti
 * leksičkom analizom.
 * 
 * @author Filip
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor bez argumenata.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Konstruktor koji prima poruku o iznimnoj situaciji.
	 * 
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}

}
