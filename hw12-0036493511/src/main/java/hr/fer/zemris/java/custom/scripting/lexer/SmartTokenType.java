package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Razred <code>SmartTokenType</code> definira različite vrste tokena koje
 * leksički analizator može vratiti.
 * 
 * @author Filip
 *
 */
public enum SmartTokenType {
	/**
	 * Vrsta tokena koji označava neku vrstu pogreške (npr. prazan ulazni tekst).
	 */
	EOF,
	/**
	 * Vrsta tokena koji predstavlja tekst u BASIC načinu rada Lexera.
	 */
	TEXT,
	/**
	 * Vrsta tokena koji počinje i završava navodnicima, u TAGS načinu rada Lexera.
	 */
	STRING,
	/**
	 * Vrsta tokena koji označava decimalni broj, u TAGS načinu rada Lexera.
	 */
	DOUBLE,
	/**
	 * Vrsta tokena koji označava cjeli broj, u TAGS načinu rada Lexera.
	 */
	INTEGER,
	/**
	 * Vrsta tokena koji označava jedan od dozvoljenih operatora(+,-,/,*,^) u TAGS
	 * načinu rada Lexera.
	 */
	OPERATOR,
	/**
	 * Vrsta tokena koji označava ime funkcije, a počinje znakom @ u TAGS načinu
	 * rada Lexera.
	 */
	FUNCTION,
	/**
	 * Vrsta tokena koji označava ime varijable u TAGS načinu rada Lexera.
	 */
	VARIABLE,
	/**
	 * Vrsta tokena koji označava završetak načina rada u TAGS te prebacuje u BASIC.
	 */
	CLOSINGTAG,
	/**
	 * Vrsta tokena koji sadrži početak načina rada u TAGS, a kraj BASIC-a.
	 */
	TAG,
	/**
	 * Vrsta tokena koji sadrži znak = u TAGS načinu rada Lexera.
	 */
	SPECIAL,
	/**
	 * Vrsta tokena koji sadrži varijablul čije je ime "FOR".
	 */
	FOR,
	/**
	 * Vrsta tokena koji sadrži varijablul čije je ime "END".
	 */
	END
}
