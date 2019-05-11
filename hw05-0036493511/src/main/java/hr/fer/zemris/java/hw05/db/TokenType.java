package hr.fer.zemris.java.hw05.db;

/**
 * Enumeracija koja definira tipove tokena.
 * 
 * @author Filip
 *
 */
public enum TokenType {
	/**
	 * Tip tokena za dojavu kraja leksičke analize.
	 */
	EOF,
	/**
	 * FirstName, lastName i jmbag sprema u tokene ovog tipa.
	 */
	ATRIBUT,
	/**
	 * Predstvalja riječ "and" neovisno o veličini slova.
	 */
	SEPARATOR,
	/**
	 * Sprema operatore za usporedbu u ovu vrstu tokena.
	 */
	OPERATOR,
	/**
	 * Tekst unutar navodnika, može sadržavati '*' .
	 */
	STRING,
	/**
	 * Broj unutar navodnika.
	 */
//	JMBAG
}
