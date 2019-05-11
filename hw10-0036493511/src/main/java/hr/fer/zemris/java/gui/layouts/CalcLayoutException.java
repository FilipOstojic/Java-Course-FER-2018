package hr.fer.zemris.java.gui.layouts;

/**
 * Razred <code>CalcLayoutException</code> predstavlja iznimku koja odgova
 * iznimci u radu kalkulatora ili razmještaju.
 * 
 * @author Filip
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            opis iznimke
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}
