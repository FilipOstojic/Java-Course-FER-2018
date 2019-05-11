package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred <code>ElementVariable</code> definira Element koji čuva u članskoj
 * varijabli varijablu.Varijabla počinje slovom, zatim sljede nula ili više
 * brojeva, slova ili podvlaka.
 * 
 * @author Filip
 *
 */
public class ElementVariable extends Element {
	/**
	 * Varijabla sadrži ime varijable.
	 */
	private String name;

	/**
	 * Konstruktor koji prima jedan argument (ime varijable).
	 * 
	 * @param value
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}

	/**
	 * Metoda vraća ime varijable koju čuva varijabla <code>name</code> i razmak
	 * (prilagođeno ispisu više elemenata zaredom).
	 */
	@Override
	public String asText() {
		return name + " ";
	}

	/**
	 * Vraća ime varijable.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

}
