package hr.fer.zemris.java.hw03.prob1;

import static hr.fer.zemris.java.hw03.prob1.TokenType.*;
import static hr.fer.zemris.java.hw03.prob1.LexerState.*;

/**
 * Razred <code>Lexer</code> predstavlja Lekser koji je predviđen za leksičku
 * analizu jezika Vlang.
 * 
 * @author Filip
 *
 */
public class Lexer {
	/**
	 * Polje znakova u koje se pohranjuje ulazni tekst.
	 */
	private char[] data;
	/**
	 * Referenca na trenutni Token.
	 */
	private Token token;
	/**
	 * Brojač pozicije unutar polja znakova.
	 */
	private int currentIndex;
	/**
	 * Pokazuje u kojem načinu rada radi lekser trenutno.
	 */
	private LexerState state;
	/**
	 * Pomoćna varijabla.
	 */
	private boolean wasExtended;

	/**
	 * Konstruktor, prima ulazni tekst. Inicijalizira način rada lexera na BASIC.
	 * 
	 * @param text
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input String must not be null.");
		} else {
			text = text.replaceAll(" +", " ");
			data = text.trim().toCharArray();
			state = BASIC;
		}
	}

	/**
	 * Od ulaznog teksta radi tokene i uvijek vraća sljedeći token ili grešku .
	 * 
	 * @return Token
	 */
	public Token nextToken() {
		if (state == BASIC) {
			if (data.length == currentIndex && token == null) {
				token = new Token(EOF, null);
				return token;
			} else if (token != null && token.getType() == EOF) {
				throw new LexerException("Next token does not exists.");
			} else if (token != null && currentIndex >= data.length) {
				if (wasExtended == true) {
					wasExtended = false;
					token = new Token(SYMBOL, Character.valueOf('#'));
					return token;
				} else {
					token = new Token(EOF, null);
					return token;
				}
			} else {
				if (wasExtended == true) {
					wasExtended = false;
					token = new Token(SYMBOL, Character.valueOf('#'));
					return token;
				}
				String result = "";
				int slashCount = 0; // stavit ga unutar if-a kad znamo da je prvo slovo?
				for (; currentIndex < data.length;) {
					char c = data[currentIndex];
					while (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
						c = data[++currentIndex];
					}
					if (Character.isLetter(c)) {
						result += c;
						currentIndex++;
						while (true) {
							if (currentIndex >= data.length) {
								token = new Token(WORD, result);
								return token;
							}
							c = data[currentIndex];
							if (Character.isLetter(c) && slashCount == 0) {
								result += c;
								currentIndex++;
							} else if (Character.isLetter(c) && slashCount != 0) {
								throw new LexerException();
							} else if (c == '\\') {
								if (currentIndex + 1 < data.length) {
									if (!Character.isDigit(data[currentIndex + 1])) {
										result += '\\';
										slashCount--;
									}
								} else if (currentIndex + 1 == data.length) {
									currentIndex++;
									token = new Token(WORD, result);
									return token;
								}
								slashCount++;
								currentIndex++;
							} else if (Character.isDigit(c) && slashCount > 0) {
								result += c;
								currentIndex++;
								slashCount--;
							} else {
								token = new Token(WORD, result);
								return token;
							}
						}
					} else if (Character.isDigit(c) && slashCount == 0) {
						while (Character.isDigit(c)) {
							result += c;
							currentIndex++;
							if (currentIndex >= data.length) {
								try {
									token = new Token(NUMBER, Long.valueOf(result));
								} catch (NumberFormatException e) {
									throw new LexerException("Number is too big.");
								}
								return token;
							}
							c = data[currentIndex];
						}
						while (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
							c = data[++currentIndex];
						}
						try {
							token = new Token(NUMBER, Long.valueOf(result));
						} catch (NumberFormatException e) {
							throw new LexerException("Number is too big.");
						}
						return token;
					} else if (Character.isDigit(c) && slashCount >= 0) {
						result += c;
						c = data[++currentIndex];
						slashCount--;
					} else if (c == '\\') {
						if (currentIndex >= data.length - 1) {
							throw new LexerException();
						}
						slashCount++;
						while (true) {
							if (currentIndex >= data.length - 1) {
								currentIndex++;
								token = new Token(WORD, result);
								return token;
							}
							c = data[++currentIndex];

							if (Character.isLetter(c) && slashCount > 0) {
								throw new LexerException();
							} else if (Character.isDigit(c) && slashCount > 0) {
								result += c;
								slashCount--;
							} else if (c == '\\') {
								slashCount++;
							} else if (Character.isLetter(c)) {
								result += c;
							} else if (Character.isDigit(c)) {
								throw new LexerException();
							} else {
								token = new Token(WORD, result);
								return token;
							}
						}
					} else if ((c >= '!' && c <= '/') || (c >= ':' && c < 'A') || (c >= '[' && c <= '\'')
							|| (c >= '{' && c <= '~')) {
						if (c == '#') {
							this.setState(EXTENDED); // (state == BASIC) ? EXTENDED : BASIC
						}
						currentIndex++;
						token = new Token(SYMBOL, c);
						return token;
					} else {
						currentIndex++;
						token = new Token(EOF, null);
						return token;
					}
				}
			}
			return token;
		} else {
			if (data.length == currentIndex && token == null) {
				token = new Token(EOF, null);
				return token;
			} else if (token != null && token.getType() == EOF) {
				throw new LexerException("Next token does not exists.");
			} else if (token != null && currentIndex >= data.length) {
				token = new Token(EOF, null);
				return token;
			} else {
				String result = "";
				for (; currentIndex < data.length;) {
					char c = data[currentIndex];
					while (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
						c = data[++currentIndex];
					}
					if (c == '#') {
						this.setState(BASIC);
						currentIndex++;
						token = new Token(SYMBOL, Character.valueOf('#'));
						return token;
					} else if (c != '\r' && c != '\n' && c != '\t' && c != ' ') {
						result += c;
						currentIndex++;
						while (true) {
							if (currentIndex >= data.length) {
								token = new Token(WORD, result);
								return token;
							}
							c = data[currentIndex];
							if (c == '#') {
								this.setState(BASIC);
								currentIndex++;
								if (result != "") {
									token = new Token(WORD, result);
									wasExtended = true;
								} else {
									token = new Token(SYMBOL, Character.valueOf('#'));
								}
								return token;
							} else if (c != '\r' && c != '\n' && c != '\t' && c != ' ') {
								result += c;
								currentIndex++;
							} else {
								token = new Token(WORD, result);
								return token;
							}
						}
					}
				}
			}
			return token;
		}
	}

	/**
	 * Metoda vraća trenutni Token.
	 * 
	 * @return Token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Metoda koja izvana može postaviti način rada lexera.
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		if (!(state instanceof LexerState)) {
			throw new IllegalArgumentException("State must be BASIC or EXTENDED, was: " + state);
		}
		this.state = state;
	}
}
