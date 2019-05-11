package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Razred <code>SmartScriptParserException</code> predstavlja grešku koja se
 * baca prilikom bilo kakve pogreške u parsiranju (tj. nepravilnom poretku
 * tokena).
 * 
 * @author Filip
 *
 */
public class SmartScriptParserException extends RuntimeException {
	
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor, prima poruku o iznimnoj situaciji.
	 * 
	 * @param message
	 *            opis iznimke
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Konstruktor bez argumenata.
	 */
	public SmartScriptParserException() {
		super();
	}

}
