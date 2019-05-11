package hr.fer.zemris.java.hw15.dao;

/**
 * Predstavlja sve greške koje se dogode u sloju za perzistenciju podataka.
 * 
 * @author Filip
 *
 */
public class DAOException extends RuntimeException {
	/**
	 * serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstrukotr.
	 */
	public DAOException() {
	}

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            poruka o pogrešci
	 * @param cause
	 *            uzrok nastanka
	 * @param enableSuppression
	 *            zastavica
	 * @param writableStackTrace
	 *            zastavica
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            poruka o pogrešci
	 * @param cause
	 *            uzrok nastanka
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            poruka o pogrešci
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param cause
	 *            uzrok nastanka
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}