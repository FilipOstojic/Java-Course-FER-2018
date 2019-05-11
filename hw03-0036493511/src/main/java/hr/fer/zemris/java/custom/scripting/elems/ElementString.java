package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred <code>ElementString</code> definira Element koji čuva u članskoj
 * varijabli String. String je zapis koji započinje i završava navodnicima.
 * 
 * @author Filip
 *
 */
public class ElementString extends Element {
	/**
	 * Varijabla <code>value</code> čuva String.
	 */
	private String value;

	/**
	 * Konstruktor koji prima jedan argument (vrijednost Stringa).
	 * 
	 * @param value
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	/**
	 * Metoda koja vraća cijelokupni String.
	 */
	@Override
	public String asText() {
		return "\"" + value + "\" ";
	}

	/**
	 * Vraća vrijednost Stringa.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

}
