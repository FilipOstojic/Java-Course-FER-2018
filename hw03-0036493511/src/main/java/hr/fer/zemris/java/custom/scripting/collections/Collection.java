package hr.fer.zemris.java.custom.scripting.collections;

/**
 * Rared <code>Collection</code> predstavlja jednostavnu kolekciju
 * objekata.
 * @author Filip Ostojić
 *
 */
public class Collection { 
	
	protected Collection(){	
	}
	
	/**
	 * Metoda provjerava je li kolekcija prazna.
	 * @return true ako je prazna, inače false
	 */
	public boolean isEmpty() {
		return this.size() == 0 ? true : false;
	}
	
	/**
	 * Vraća broj elemenata pohranjenih u kolekciji. Ovdje implementirana da 
	 * vraća 0.
	 * @return 0
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Dodaje objekt u kolekciju. Ovdje implementirana ne radi ništa.
	 * @param value
	 */
	public void add(Object value) {
	}
	
	/**
	 * Provjerava sadrži li kolekcija traženi objekt.
	 * @param value (Object)
	 * @return false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Briše objekt iz kolekcije. Ovdje implementirana uvijek vraća false.
	 * @param value
	 * @return false
	 */
	public boolean remove(Object value) {
		if (this.contains(value)) {
			//remove
			return true; //nikad se neće izvest
		}
		return false;
	}
	
	/**
	 * Alocira novo polje veličine kolekcije. Popuni polje s elementima kolekcije
	 * koje vraća. Ovdje implementirana baca iznimku.
	 * @return UnsupportedOperationException
	 */
	public Object[] toArray() {
		if (this==null || this.isEmpty()) throw new UnsupportedOperationException();
		Object[] array = new Object[this.size()];
		//napuni
		return array; //nikad se ne izvede
	}
	
	/**
	 * Metoda zove processor.process(.) za svaki element kolekcije.
	 * Ovdje implementirana kao prazna metoda.
	 * @param processor
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * Metoda dodaje u trenutnu kolekciju sve elemente iz dane kolekcije.
	 * Dana kolekcija se ne mijenja.
	 * @param other (Collection)
	 */
	public void addAll(Collection other) {
		
		class NewProcessor extends Processor{
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new NewProcessor());
	}
	
	/**
	 * Briše sve elemente kolekcije. Ovdje implementirana ne radi ništa.
	 */
	public void clear() {
	}
}
