package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred <code>ElementConstantInteger</code> definira Element koji čuva u
 * članskoj varijabli objekt tima Integer. Integeri su brojevi bez decimalne
 * točke.
 * 
 * @author Filip
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * Varijabla <code>value</code> čuva cjelobrojnu vrijednost elementa.
	 */
	private int value;

	/**
	 * Konstruktor koji prima jedan argument.
	 * 
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * Metoda vraća String vrijednost varijable <code>value</code>.
	 */
	@Override
	public String asText() {
		return String.valueOf(value + " ");
	}

	/**
	 * Vraća cjelobrojnu vrijednost varijable <code>value</code>.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

}
