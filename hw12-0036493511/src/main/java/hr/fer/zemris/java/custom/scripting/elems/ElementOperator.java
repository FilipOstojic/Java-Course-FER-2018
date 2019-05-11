package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred <code>ElementOperator</code> definira Element koji čuva u članskoj
 * varijabli operator. Dozvoljeni operator su : +, -, *, /, ^.
 * 
 * @author Filip
 *
 */
public class ElementOperator extends Element {
	/**
	 * Varijabla <code>symbol</code> čuva jedan od operatora.
	 */
	private String symbol;

	/**
	 * Konstruktor koji prima jedan argument (vrijednost simbola).
	 * 
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Metoda vraća String vrijednos unesenog operatora.
	 */
	@Override
	public String asText() {
		return symbol + " ";
	}

	/**
	 * Metoda vraća String vrijednos unesenog operatora.
	 */
	public String getSymbol() {
		return symbol;
	}

}
