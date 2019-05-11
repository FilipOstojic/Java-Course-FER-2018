package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Razred <code>SmartToken</code> predstavlja pomoćni razred u koji pohranjujemo
 * sadržaj te tip tokena.
 * 
 * @author Filip
 *
 */
public class SmartToken {
	/**
	 * Varijabla u koju se sprema tip tokena.
	 */
	private SmartTokenType type;
	/**
	 * Varijabla u koju se sprema vrijednost tokena.
	 */
	private Object value;

	/**
	 * Konstruktor, prima tip i vrijednost novog tokena.
	 * 
	 * @param type
	 * @param value
	 */
	public SmartToken(SmartTokenType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	/**
	 * Metoda koja vraća tip tokena.
	 * 
	 * @return
	 */
	public SmartTokenType getType() {
		return type;
	}

	/**
	 * Metoda koja vraća vrijednost tokena.
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Metoda za ljepši ispis tokena, kao uređeni par (tip, vrijednos).
	 */
	@Override
	public String toString() {
		return String.format("(%s, %s)", this.getType(), this.getValue());
	}
}
