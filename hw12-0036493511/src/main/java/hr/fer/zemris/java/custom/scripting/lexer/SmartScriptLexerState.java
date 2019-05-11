package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeracija koja definira 2 varijable koje predstavljaju 2 načina rada
 * Lexera (TAGS, BASIC).
 * 
 * @author Filip
 *
 */
public enum SmartScriptLexerState {
	/**
	 * Varijabla označava početni način rada Lexera dok obrađuje text.
	 */
	BASIC,
	/**
	 * Varijabla označava rad Leksera dok obrađuje Tagove.
	 */
	TAGS
}
