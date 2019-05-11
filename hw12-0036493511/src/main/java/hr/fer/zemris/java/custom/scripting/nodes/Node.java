package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;

/**
 * Bazni razred <code>Node</code> predstavlja čvor. Ima jednu varijablu:
 * kolekciju objekata.
 * 
 * @author Filip
 *
 */
public abstract class Node {
	/**
	 * Kolekcija koja čuva djecu ovog čvora.
	 */
	private ArrayIndexedCollection collection = new ArrayIndexedCollection();;

	/**
	 * Metoda dodaje novi čvor u kolekciju djece.
	 * 
	 * @param child
	 */
	public void addChildNode(Node child) {
		collection.add(child);
	}

	/**
	 * Metoda vraća broj djece čvora.
	 * 
	 * @return broj djece
	 */
	public int numberOfChildren() {
		return collection.size();
	}

	/**
	 * Metoda vraća dijete čvora pod brojem index.
	 * 
	 * @param index
	 * @return Node
	 */
	public Node getChild(int index) {
		if (index < 0 || index > collection.size() - 1) {
			throw new IllegalArgumentException("Index is not in correct range.");
		}
		return (Node) collection.get(index);
	}

	/**
	 * Apstraktna metoda koja ovisno o vrsti čvora poziva njegovu implementaciju
	 * metode.
	 * 
	 * @param visitor
	 *            {@link INodeVisitor}
	 */
	public abstract void accept(INodeVisitor visitor);
}
