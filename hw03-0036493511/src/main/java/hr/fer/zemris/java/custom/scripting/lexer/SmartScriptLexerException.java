package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Razred <code>SmartScriptLexerException</code> predtavlja iznimku koja se baca
 * prilikom bilo kakve leksičke neispravnosti tijekom rada Lexera.
 * 
 * @author Filip
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor prima poruku o iznimci.
	 * 
	 * @param message
	 *            opis iznimke
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

	/**
	 * Konstruktor bez argumenata.
	 */
	public SmartScriptLexerException() {
		super();
	}
}
