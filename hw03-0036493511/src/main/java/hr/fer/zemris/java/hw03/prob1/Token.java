package hr.fer.zemris.java.hw03.prob1;

/**
 * Razred <code>Token</code> pohranjuje vrijednost i tip tokena.
 * 
 * @author Filip
 *
 */
public class Token {
	/**
	 * Varijabla čuva tip tokena.
	 */
	private TokenType type;
	/**
	 * Varijabla čuva vrijednost tokena.
	 */
	private Object value;

	/**
	 * Konstruktor koji prima tip i vrijednost tokena.
	 * 
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	/**
	 * Metoda vraća vrstu tokena.
	 * 
	 * @return {@link TokenType}
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Metoda vraća vrijednost tokena.
	 * 
	 * @return Object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Nadjačana metoda toString. Ispis u obliku uređenog para (tip, vrijednost).
	 */
	@Override
	public String toString() {
		return String.format("(%s, %s)", this.getType(), this.getValue());
	}

}
