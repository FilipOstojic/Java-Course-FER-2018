package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Razred <code>EchoNode</code> nasljeđuje razred <code>Element</code> te
 * predstavlja čvor koji sadrži polje elemenata. SPECIAL je tip tokena kojim
 * započinje ovaj čvor.
 * 
 * @author Filip
 *
 */
public class EchoNode extends Node {
	/**
	 * Varijabla polje elemenata.
	 */
	private Element[] elements;

	/**
	 * Konstruktor koji prima polje elemenata.
	 * 
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Metoda vraća polje elemenata čvora.
	 * 
	 * @return
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Metoda za ispis.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("{$=");
		while (elements[i] != null) {
			if (elements[i] instanceof ElementVariable || elements[i] instanceof ElementConstantInteger
					|| elements[i] instanceof ElementConstantDouble || elements[i] instanceof ElementFunction
					|| elements[i] instanceof ElementOperator || elements[i] instanceof ElementString) {
				sb.append(elements[i].asText() + " ");
			}
			i++;
		}
		sb.append("$}");
		return sb.toString();
	}
}
