package hr.fer.zemris.java.hw05.db;

/**
 * Razred <code>QueryLexer</code> predstavlja lekser koji vrši leksičku analizu
 * primljnog teksta te vraća Tokene koji su nekog od tipa {@link TokenType} ili
 * baca <code>IllegalArgumentException</code>.
 * 
 * @author Filip
 *
 */
public class QueryLexer {
	/**
	 * Čuva ulazni tekst u polju znakova.
	 */
	private char[] charArray;
	/**
	 * Veličina polja znakova.
	 */
	private int size;
	/**
	 * Index trenutnog znaka u polju znakova.
	 */
	private int currentPosition;

	/**
	 * Konstruktor, stavara polje znakova od ulaznog stringa.
	 * 
	 * @param line
	 *            ulazni tekst
	 */
	public QueryLexer(String line) {
		charArray = line.toCharArray();
		size = charArray.length;
	}

	/**
	 * Metoda koja prolazi kroz polje ulaznih znakova, čita ih te razdvaja u zasebne
	 * cjeline (Tokene), prilikom čega može izazvati IllegalArgumentException
	 * iznimku ako se dogodi neka od leksičkih pogrešaka.
	 * 
	 * @return {@link Token}
	 */
	public Token next() {
		for (; currentPosition < size;) {
			skipWhitespaces();
			if (Character.isLetter(charArray[currentPosition])) {
				return getTokenFromWord();
			} else if (charArray[currentPosition] == '>' || charArray[currentPosition] == '<'
					|| charArray[currentPosition] == '=' || charArray[currentPosition] == '!') {
				return getTokenFromOperator();
			} else if (charArray[currentPosition] == '\"') {
				return getTokenFromString();
			} else {
				throw new IllegalArgumentException("Neither of the tokens begins with: " + charArray[currentPosition]);
			}
		}
		return new Token(TokenType.EOF, null);
	}

	/**
	 * Pomoćna metoda koja vraća token tipa STRING ili JMBAG iz teksta unutar
	 * navodnika.
	 * 
	 * @return {@link Token}
	 */
	private Token getTokenFromString() {
		if (!hasNextChar())
			throw new IllegalArgumentException("String is empty or not closed.");
		currentPosition++;
		String result = "";
		while (charArray[currentPosition] != '\"') {
			result += charArray[currentPosition];
			currentPosition++;
		}
		currentPosition++;
		return new Token(TokenType.STRING, result);
	}

	/**
	 * Pomoćna metoda za stvaranje tokena tipa OPERATOR iz znakova.
	 * 
	 * @return {@link Token}
	 */
	private Token getTokenFromOperator() {
		switch (charArray[currentPosition]) {
		case '>':
			if (hasNextChar() && charArray[currentPosition + 1] == '=') {
				currentPosition += 2;
				return new Token(TokenType.OPERATOR, ">=");
			}
			currentPosition += 1;
			return new Token(TokenType.OPERATOR, ">");
		case '<':
			if (hasNextChar() && charArray[currentPosition + 1] == '=') {
				currentPosition += 2;
				return new Token(TokenType.OPERATOR, "<=");
			}
			currentPosition += 1;
			return new Token(TokenType.OPERATOR, "<");
		case '=':
			currentPosition += 1;
			return new Token(TokenType.OPERATOR, "=");
		case '!':
			if (hasNextChar() && charArray[currentPosition + 1] == '=') {
				currentPosition += 2;
				return new Token(TokenType.OPERATOR, "!=");
			}
			currentPosition += 1;
			throw new IllegalArgumentException("After ! expected =, was: " + charArray[currentPosition]);
		}
		return null;
	}

	/**
	 * Pomoćna metoda za stvaranje tokena tipova SEPARATOR, ATRIBUT, OPERATOR
	 * izdijela teksta koji su slova.
	 * 
	 * @return {@link Token}
	 */
	private Token getTokenFromWord() {
		switch (charArray[currentPosition]) {
		case 'a':
			if (checkNext("and", 2, true)) {
				return new Token(TokenType.SEPARATOR, "and");
			}
			throw new IllegalArgumentException("Lexer cant make any token.");
		case 'A':
			if (checkNext("and", 2, true)) {
				return new Token(TokenType.SEPARATOR, "and");
			}
			throw new IllegalArgumentException("Lexer cant make any token.");
		case 'j':
			if (checkNext("jmbag", 4, false)) {
				return new Token(TokenType.ATRIBUT, "jmbag");
			}
			throw new IllegalArgumentException("Lexer cant make any token.");
		case 'l':
			if (checkNext("lastName", 7, false)) {
				return new Token(TokenType.ATRIBUT, "lastName");
			}
			throw new IllegalArgumentException("Lexer cant make any token.");
		case 'f':
			if (checkNext("firstName", 8, false)) {
				return new Token(TokenType.ATRIBUT, "firstName");
			}
			throw new IllegalArgumentException("Lexer cant make any token.");
		case 'L':
			if (checkNext("LIKE", 3, false)) {
				return new Token(TokenType.OPERATOR, "LIKE");
			}
			throw new IllegalArgumentException("Lexer cant make any token.");
		default:
			throw new IllegalArgumentException(
					"Lexer cant make any token with starting letter: " + charArray[currentPosition]);
		}
	}

	/**
	 * Pomoćna metoda koja provjerava postoji li određen broj znakova u budućnosti i
	 * jesu li oni jednaki očekivanom.
	 * 
	 * @param expected
	 *            očekivani string
	 * @param len
	 *            broj znakova za koje se gledaju postoje li unaprijed
	 * @param ignoreCase
	 *            zastavica, ako je true kod usporedbe nije važna veličina slova, u
	 *            suprotnom je
	 * @return true ako određeni broj znakova postoji i jednak je očekivanom, inače
	 *         false
	 * 
	 */
	private boolean checkNext(String expected, int len, boolean ignoreCase) {
		if (currentPosition + len < size) {
			int end = currentPosition + len;
			String actual = "";
			for (; currentPosition <= end; currentPosition++) {
				actual += charArray[currentPosition];
			}
			if (ignoreCase) {
				return expected.equalsIgnoreCase(actual);
			}
			return expected.equals(actual);
		}
		return false;
	}

	/**
	 * Metoda preskače praznine u polju.
	 */
	private void skipWhitespaces() {
		while (Character.isWhitespace(charArray[currentPosition])) {
			currentPosition++;
		}
	}

	/**
	 * Metoda provjerava postoji li sljedeći znak u polju.
	 * 
	 * @return true ako postoji sljedeći znak, inače false
	 */
	private boolean hasNextChar() {
		return currentPosition + 1 < size;
	}
}

/**
 * Pomoćni razred koji čuva vrijednost i tip tokena. Predstavlja najmanju
 * cijelinu koja ima značenje.
 * 
 * @author Filip
 *
 */
class Token {
	/**
	 * Varijabla čuva vrijednost tokena.
	 */
	String value;
	/**
	 * Varijabla čuva tip tokena: {@link TokenType}
	 */
	TokenType type;

	/**
	 * Konstruktor, prima vrijednost i tip tokena.
	 * 
	 * @param type
	 *            {@link TokenType}
	 * @param value
	 *            vrijednost tokena
	 */
	public Token(TokenType type, String value) {
		this.value = value;
		this.type = type;
	}

	/**
	 * Metoda vraća vrijednost tokena.
	 * 
	 * @return vrijednost tokena
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Metoda vraća tip tokena.
	 * 
	 * @return {@link TokenType}
	 */
	public TokenType getType() {
		return type;
	}

}