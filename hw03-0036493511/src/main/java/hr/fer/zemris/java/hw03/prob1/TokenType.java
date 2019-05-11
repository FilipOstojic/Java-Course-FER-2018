package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeracija definira vrste tokena.
 * 
 * @author Filip
 *
 */
public enum TokenType {
	/**
	 * Vrsta tokena koji predstavlja pogrešku.
	 */
	EOF,
	/**
	 * Vrsta tokena koji predstavlja riječ.
	 */
	WORD,
	/**
	 * Vrsta tokena koji predstavlja broj.
	 */
	NUMBER,
	/**
	 * Vrsta tokena koji predstavlja simbol.
	 */
	SYMBOL
}
