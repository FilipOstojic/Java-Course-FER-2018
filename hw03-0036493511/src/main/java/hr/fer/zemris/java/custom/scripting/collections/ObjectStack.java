package hr.fer.zemris.java.custom.scripting.collections;

/**
 * Razred <code>ObjectStack</code> predstavlja implementaciju kolekcije, tj stog.
 * 
 * @author Filip Ostojić
 *
 */
public class ObjectStack {
	private ArrayIndexedCollection array;
	
	/**
	 * Konstruktor stvara novi stog.
	 */
	public ObjectStack() {
		array = new ArrayIndexedCollection();
	}
	
	/**
	 * Dodaje na element vrh stoga.
	 * @param value
	 */
	public void push(Object value) {
		array.add(value);
	}
	
	/**
	 * Uklanja element s vrha stoga.
	 * @return uklonjeni element ili iznimku ako je stog praza
	 */
	public Object pop() {
		if(this.isEmpty()) {
			throw new EmptyStackException();
		}
		Object returnValue = array.get(array.size()-1);
		array.remove(this.size()-1);
		return returnValue;
	}
	
	/**
	 * Metoda računa je li kolekcija prazna.
	 * @return true ako je stog prazan, inače false
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Vraća broj elemenata na stogu.
	 * @return broj elemenata (int)
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * Vraća element s vrha stoga, ali ga ne briše.
	 * @return element s vrha stoga
	 */
	public Object peek() {
		return array.get(array.size()-1);
	}
	
	/**
	 * Briše cijeli stog.
	 */
	public void clear() {
		array.clear();
	}
}
