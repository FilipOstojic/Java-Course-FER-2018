package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred <code>ElementFunction</code> definira Element koji čuva u članskoj
 * varijabli oznaku @ i ime funkcije.
 * 
 * @author Filip
 *
 */
public class ElementFunction extends Element {
	/**
	 * Varijabla <code>name</code> čuva oznaku i naziv funkcije.
	 */
	private String name;

	/**
	 * Konstruktor koji prima jedan argument.
	 * 
	 * @param name
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	/**
	 * Metoda vraća ime funkcije koju čuva varijabla <code>name</code>.
	 */
	@Override
	public String asText() {
		return name + " ";
	}

	/**
	 * Vraća ime funkcije.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

}
