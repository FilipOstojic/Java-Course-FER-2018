package hr.fer.zemris.java.custom.scripting.lexer;

import static hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState.*;
import static hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType.*;

/**
 * Razred <code>SmartScriptLexer</code> predstavlja lekser koji vrši leksičku
 * analizu primljnog teksta te vraća Tokene koji su nekog od tipa
 * {@link SmartTokenType}.
 * 
 * @author Filip
 *
 */
public class SmartScriptLexer {
	/**
	 * Čuva ulazni tekst u polju znakova.
	 */
	private char[] data;
	/**
	 * Element leksičke analize.
	 */
	private SmartToken token;
	/**
	 * Pokazuje vrijednost indexa polja, elementa koji se trenutno obrađuje.
	 */
	private int currentIndex;
	/**
	 * Označava trenutni način rada parsera.
	 */
	private SmartScriptLexerState state;

	/**
	 * Konstruktor koji prima ulazni tekst, te ga kopira u polje znakova te
	 * inicijalizira početni način rada Lexera.
	 * 
	 * @param text
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input String must not be null.");
		} else {
			data = text.toCharArray();
			state = BASIC;
		}
	}

	/**
	 * Metoda koja prolazi kroz polje ulaznih znakova, čita ih te razdvaja u zasebne
	 * cjeline (Tokene), prilikom čega može izazvati SmartScriptLexerException
	 * grešku prilikom leksičkih pogrešaka iz polja.
	 * 
	 * @return {@link SmartToken}
	 */
	public SmartToken nextToken() {
		if (state == BASIC) {
			if (data.length == currentIndex && token == null) {
				token = new SmartToken(EOF, null);
				return token;
			} else if (token != null && token.getType() == EOF) {
				throw new SmartScriptLexerException("Next token does not exists.");
			} else if (token != null && currentIndex >= data.length) {
				token = new SmartToken(EOF, null);
				return token;
			} else {
				String result = "";
				for (; currentIndex < data.length;) {
					char c = data[currentIndex];
					if (c == '\\') {
						currentIndex++;
						if (currentIndex == data.length)
							throw new SmartScriptLexerException("Index out of bounds.");
						c = data[currentIndex];
						if (c == '\\') {
							result += "\\";
							currentIndex += 1;
							continue;
						} else if (c == '{') {
							result += "{";
							currentIndex += 1;
							continue;
						} else {
							throw new SmartScriptLexerException("After \\, can only be \\ or { .");
						}
					} else if (c == '{') {
						currentIndex++;
						if (currentIndex == data.length)
							throw new SmartScriptLexerException("Index out of bounds.");
						c = data[currentIndex];
						if (c == '$') {
							if (result != "") {
								currentIndex--;
								token = new SmartToken(TEXT, result);
								return token;
							} else {
								currentIndex += 1;
								state = TAGS;
								token = new SmartToken(TAG, "{$");
								return token;
							}
						} else {
							result += c;
						}
					} else {
						result += c;
						currentIndex++;
					}
				}
				token = new SmartToken(TEXT, result);
				return token;
			}
		} else {
			if (data.length == currentIndex && token == null) {
				token = new SmartToken(EOF, null);
				return token;
			} else if (token != null && token.getType() == EOF) {
				throw new SmartScriptLexerException("Next token does not exists.");
			} else if (token != null && currentIndex >= data.length) {
				token = new SmartToken(EOF, null);
				return token;
			} else {
				String result = "";
				for (; currentIndex < data.length;) {
					char c = data[currentIndex];
					while (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
						currentIndex++;
						if (currentIndex == data.length)
							throw new SmartScriptLexerException("Closing tag is missing. ");
						c = data[currentIndex];
					}
					if (c == '@') {
						result += c;
						currentIndex++;
						if (currentIndex == data.length)
							throw new SmartScriptLexerException("Invalid function token.");
						c = data[currentIndex];
						if (Character.isLetter(c)) {
							result += c;
							currentIndex++;
							if (currentIndex == data.length) {
								token = new SmartToken(FUNCTION, result);
								return token;
							}
							c = data[currentIndex];
							while (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
								result += c;
								currentIndex++;
								if (currentIndex == data.length) {
									token = new SmartToken(FUNCTION, result);
									return token;
								}
								c = data[currentIndex];
							}
							if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
								currentIndex++;
								token = new SmartToken(FUNCTION, result);
								return token;
							} else if (c == '$') {
								currentIndex++;
								if (currentIndex == data.length)
									throw new SmartScriptLexerException("Invalid closing token.");
								c = data[currentIndex];
								if (c == '}') {
									currentIndex++;
									if (result != "") {
										currentIndex -= 2;
										token = new SmartToken(SmartTokenType.FUNCTION, result);
										return token;
									} else {
										state = BASIC;
										token = new SmartToken(CLOSINGTAG, "$}");
										return token;
									}
								} else {
									throw new SmartScriptLexerException("Invalid operator token.");
								}
							} else {
								throw new SmartScriptLexerException("Invalid function token.");
							}
						} else {
							throw new SmartScriptLexerException("After @ must be a letter.");
						}
					} else if (Character.isLetter(c)) {
						result += c;
						currentIndex++;
						if (currentIndex == data.length) {
							token = new SmartToken(VARIABLE, result);
							return token;
						}
						c = data[currentIndex];
						while (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
							result += c;
							currentIndex++;
							if (currentIndex == data.length) {
								if (result.equalsIgnoreCase("for")) {
									token = new SmartToken(FOR, "FOR");
									return token;
								} else if (result.equalsIgnoreCase("end")) {
									token = new SmartToken(END, "END");
									return token;
								}
								token = new SmartToken(VARIABLE, result);
								return token;
							}
							c = data[currentIndex];
						}
						if (result.equalsIgnoreCase("for")) {
							token = new SmartToken(FOR, result);
							return token;
						} else if (result.equalsIgnoreCase("end")) {
							token = new SmartToken(END, result);
							return token;
						} else if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
							currentIndex++;
							token = new SmartToken(VARIABLE, result);
							return token;
						} else if (c == '$') {
							currentIndex++;
							if (currentIndex == data.length)
								throw new SmartScriptLexerException("Invalid closing token.");
							c = data[currentIndex];
							if (c == '}') {
								currentIndex++;
								if (result != "") {
									currentIndex -= 2;
									token = new SmartToken(VARIABLE, result);
									return token;
								} else {
									state = BASIC;
									token = new SmartToken(CLOSINGTAG, "$}");
									return token;
								}
							} else {
								throw new SmartScriptLexerException("Invalid operator token.");
							}
						} else {
							throw new SmartScriptLexerException("Invalid variable token.");
						}
					} else if (c == '=') {
						result += c;
						currentIndex++;
						token = new SmartToken(SPECIAL, result);
						return token;
					} else if (c == '+' || c == '*' || c == '^' || c == '/') {
						result += c;
						currentIndex++;
						if (currentIndex == data.length) {
							token = new SmartToken(OPERATOR, result);
							return token;
						}
						c = data[currentIndex];
						if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
							currentIndex++;
							token = new SmartToken(OPERATOR, result);
							return token;
						} else if (c == '$') {
							currentIndex++;
							if (currentIndex == data.length)
								throw new SmartScriptLexerException("Invalid closing token.");
							c = data[currentIndex];
							if (c == '}') {
								currentIndex++;
								if (result != "") {
									currentIndex -= 2;
									token = new SmartToken(OPERATOR, result);
									return token;
								} else {
									state = BASIC;
									token = new SmartToken(CLOSINGTAG, "$}");
									return token;
								}
							} else {
								throw new SmartScriptLexerException("Invalid operator token.");
							}
						} else {
							throw new SmartScriptLexerException("Invalid operator token.");
						}
					} else if (c == '-') {
						result += c;
						currentIndex++;
						if (currentIndex == data.length) {
							token = new SmartToken(OPERATOR, result);
							return token;
						}
						c = data[currentIndex];
						if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
							currentIndex++;
							token = new SmartToken(OPERATOR, result);
							return token;
						} else {
							return getNumberToken(c, result);
						}
					} else if (c == '$') {
						currentIndex++;
						if (currentIndex == data.length)
							throw new SmartScriptLexerException("Invalid closing tag token.");
						c = data[currentIndex];
						if (c == '}') {
							currentIndex++;
							state = BASIC;
							token = new SmartToken(CLOSINGTAG, "$}");
							return token;
						} else {
							throw new SmartScriptLexerException("Invalid closing tag token.");
						}
					} else if (Character.isDigit(c)) {
						return getNumberToken(c, result);
					} else if (c == '\"') {
						currentIndex++;
						if (currentIndex == data.length)
							throw new SmartScriptLexerException("Invalid String token.");
						c = data[currentIndex];
						while (c != '\"') {
							if (c == '\\') {
								currentIndex++;
								if (currentIndex == data.length)
									throw new SmartScriptLexerException("Invalid String token.");
								c = data[currentIndex];
								result += '\\';
							} else {
								result += c;
								currentIndex++;
								if (currentIndex == data.length)
									throw new SmartScriptLexerException("Invalid String token.");
								c = data[currentIndex];
							}
						}
						currentIndex++;
						token = new SmartToken(STRING, result);
						return token;
					} else {
						throw new SmartScriptLexerException("Invalid input, cannot be resolved in token.");
					}
				}
			}
		}
		return token;
	}

	/**
	 * Metoda koja vraća token tipa INTEGER ili DOUBLE ili baca iznimku.
	 * 
	 * @param c
	 * @param result
	 * @return SmartToken
	 */
	private SmartToken getNumberToken(char c, String result) {
		if (Character.isDigit(c)) {
			result += c;
			currentIndex++;
			if (currentIndex == data.length) {
				token = new SmartToken(INTEGER, Integer.parseInt(result));
				return token;
			}
			c = data[currentIndex];
			while (Character.isDigit(c)) {
				result += c;
				currentIndex++;
				if (currentIndex == data.length) {
					token = new SmartToken(INTEGER, Integer.parseInt(result));
					return token;
				}
				c = data[currentIndex];
			}
			if (c == '.') {
				result += c;
				currentIndex++;
				if (currentIndex == data.length)
					throw new SmartScriptLexerException("Invalid double token.");
				c = data[currentIndex];
				if (Character.isDigit(c)) {
					result += c;
					currentIndex++;
					if (currentIndex == data.length) {
						token = new SmartToken(DOUBLE, Double.parseDouble(result));
						return token;
					}
					c = data[currentIndex];
					while (Character.isDigit(c)) {
						result += c;
						currentIndex++;
						if (currentIndex == data.length) {
							token = new SmartToken(DOUBLE, Double.parseDouble(result));
							return token;
						}
						c = data[currentIndex];
					}
					if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
						currentIndex++;
						token = new SmartToken(DOUBLE, Double.parseDouble(result));
						return token;
					} else if (c == '$') {
						currentIndex++;
						if (currentIndex == data.length)
							throw new SmartScriptLexerException("Invalid closing token.");
						c = data[currentIndex];
						if (c == '}') {
							currentIndex++;
							if (result != "") {
								currentIndex -= 2;
								token = new SmartToken(DOUBLE, Double.parseDouble(result));
								return token;
							} else {
								state = BASIC;
								token = new SmartToken(CLOSINGTAG, "$}");
								return token;
							}
						} else {
							throw new SmartScriptLexerException("Invalid closing tag token.");
						}
					} else {
						throw new SmartScriptLexerException("Invalid double token.");
					}
				} else {
					throw new SmartScriptLexerException("Invalid double token.");
				}
			} else if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
				token = new SmartToken(INTEGER, Integer.parseInt(result));
				return token;
			} else if (c == '$') {
				currentIndex++;
				if (currentIndex == data.length)
					throw new SmartScriptLexerException("Invalid closing token.");
				c = data[currentIndex];
				if (c == '}') {
					currentIndex++;
					if (result != "") {
						currentIndex -= 2;
						token = new SmartToken(INTEGER, Integer.parseInt(result));
						return token;
					} else {
						state = BASIC;
						token = new SmartToken(CLOSINGTAG, "$}");
						return token;
					}
				} else {
					throw new SmartScriptLexerException("Invalid closing tag token.");
				}
			} else {
				throw new SmartScriptLexerException("Invalid integer token.");
			}
		} else {
			throw new SmartScriptLexerException("Invalid operator token.");
		}
	}

	/**
	 * Vraća token.
	 * 
	 * @return SmartToken
	 */
	public SmartToken getToken() {
		return token;
	}

	/**
	 * Metoda koja služi za vanjsko postavljanje načina rada Lexera.
	 * 
	 * @param state
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == TAGS) {
			this.state = TAGS;
		} else if (state == BASIC) {
			this.state = BASIC;
		} else {
			throw new SmartScriptLexerException("Invalid state. Correct states are TAGS, BASIC;  was: " + state);
		}
	}
}
