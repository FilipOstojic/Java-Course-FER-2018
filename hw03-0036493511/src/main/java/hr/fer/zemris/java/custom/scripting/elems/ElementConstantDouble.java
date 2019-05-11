package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred <code>ElementConstantDouble</code> nasljeđuje razred
 * <code>Element</code>. i čuva u članskoj varijabli double vrijednost. Doublovi
 * su brojevi koji imaju barem 1 ili više brojeva, točku te opet barem 1 ili
 * više brojeva.
 * 
 * @author Filip
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * Varijabla <code>value</code> čuva double vrijednost elementa.
	 */
	private double value;

	/**
	 * Konstruktor koji prima jedan argument.
	 * 
	 * @param value
	 */
	public ElementConstantDouble(double value) {
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
	 * Vraća double vrijednost varijable <code>value</code>.
	 * 
	 * @return value
	 */
	public double getValue() {
		return value;
	}

}
