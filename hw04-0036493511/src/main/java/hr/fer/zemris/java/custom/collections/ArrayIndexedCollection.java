package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Razred <code>ArrayIndexedCollection</code> predstavlja implementaciju kolekcije baziranu na polju.
 * Kolekcija može sadržavati duplikate, ali ne null vrijednosti.
 * @author Filip Ostojić
 *
 */
public class ArrayIndexedCollection extends Collection {
	
	private int size;
	private int capacity;
	private Object[] elements;
	
	/**
	 * Konstruktor stvara novu kolekciju i kapacitet polja
	 * elemenata incijalizira na 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Konstruktor, prima željeni kapacitet polja.
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("The capacity should not be less than 1.");
		}
		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
		this.size = 0;
	}
	
	/**
	 * Konstruktor stvara novu kolekciju i kapacitet polja je max(16,veličina druge kolekcije).
	 * @param col
	 */
	public ArrayIndexedCollection (Collection col) {
		this(col, 16);
	}
	
	/**
	 * Konstruktor stvara novu kolekciju i kapacitet polja je max(initialCapacity, veličina druge kolekcije).
	 * @param col 
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(Collection col, int initialCapacity) {
		if (col == null) {
			throw new NullPointerException("The collection shouldn't be null.");
		} else if(col.size() > initialCapacity) {
			this.capacity = col.size();
		} else {
			this.capacity = initialCapacity;
		}
		
		this.elements = new Object[capacity];
		if (col instanceof ArrayIndexedCollection) {
			for (int i = 0; i < col.size() ; i++) {
//				this.elements[i]=((ArrayIndexedCollection)col).elements[i];
				this.add(((ArrayIndexedCollection) col).elements[i]);
			}
		} else {
			for (int i = 0; i < col.size() ; i++) {
//				this.elements[i]=((LinkedListIndexedCollection)col).get(i);
				this.add(((LinkedListIndexedCollection) col).get(i));
			}
		}
		this.size = col.size();
	}
	
	/**
	 * Vraća broj elemenata pohranjenih u kolekciji.
	 */
	@Override
	public int size() {
//		if (this.elements.length < 0 || this==null) {
//			throw new IllegalArgumentException("Veličina kolekcije je upitna.");
//		} else {
//			int size = 0;
//			for (int i = 0 ; i< this.elements.length ; i++) {
//				if (elements[i] != null) size++;
//			}
//			return size;
//		}
		return size;
	}
	
	/**
	 * Dodaje element u kolekciju, na kraj.
	 */
	@Override
	public void add(Object value) {
		if (value==null) {
			throw new NullPointerException("The value should not be null.");
		} else {
			if (this.size() == this.capacity) {
				this.resize();    
			} 
			this.elements[this.size()]=value;
			this.size++;  
		}
	}
	
	/**
	 * Metoda vraća vrijedost elementa u kolekciji na traženoj poziciji.
	 * @param index
	 * @return vrijednost objekta na mjestu index
	 */
	public Object get(int index) {
		if(index < 0 || index > this.size()-1) {
			throw new IndexOutOfBoundsException("Index should be between 0 and "+this.size()+"."); //this.size()-1??
		}
		return this.elements[index];
	}
	
	/**
	 * Briše sve elemente kolekcije.
	 */
	@Override
	public void clear() {
		int len = this.size();
		for (int i = 0 ; i < len ; i++) {
			this.elements[i]=null;
		}
		this.size=0;
	}
	
	/**
	 * Umeće novi element na željenu poziciju u kolekciji.
	 * Pozicija je u rasponu od 0 do veličine kolekcije.
	 * @param value
	 * @param position
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size()) {
			throw new IndexOutOfBoundsException("Position should be between 0 and "+this.size()); 
		} else if( this.size() == this.capacity) {
			this.resize();
			if (position == this.size()) {
//				this.elements[this.size()]=value;    
				this.add(value);
			} else {
				for (int i = this.size()-1 ; i >= position ; i--) {
					this.elements[i+1] = this.elements[i];
				}
				this.elements[position] = value;
			}
		} else {
			if (position == this.size()) {
				this.elements[this.size()]=value;
			} else {
				for (int i = this.size()-1 ; i >= position ; i--) {
					this.elements[i+1] = this.elements[i];
				}
				this.elements[position] = value;
			}									
		}
		this.size++;
	}
	
	/**
	 * Metoda vraća index zadanog elementa kolekcije.
	 * @param value objekt čiji index želimo naći
	 * @return broj pozicije ako element postoji, -1 inače
	 */
	public int indexOf(Object value) {
		if (value == null) {
			throw new NullPointerException("This element is not found.");
		}
		for (int i = 0 ; i < this.size() ; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Uklanja element iz kolekcije koji se na nalazi na mjestu index.
	 * @param index (int)
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size()-1) {
			throw new IndexOutOfBoundsException("Index should be between 0 and "+this.size()); //this.size()-1
		} else if (index == this.size()-1) {
			this.elements[index]=null;
		} else {
			int len = this.size()-1;
			this.elements[index]=null;
			for (int i = index; i<=len ; i++) {
				if (i==len) {
					this.elements[i]=null;
				} else {
					this.elements[i]=this.elements[i+1];
				}
			}
		}
		this.size--;
	} 
	
	/**
	 * Ako obriše element u kolekciji vraća true, inače false.
	 */
	@Override
	public boolean remove(Object value) {
		try {
			this.remove(this.indexOf(value));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Provjerava sadrži li kolekcija traženi objekt.
	 * @param value (Object)
	 * @return true ako sadrži, inače false
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0 ; i < this.size() ; i++) {
			if(this.elements[i].equals(value)) return true;
		}
		return false;
	}
	
	/**
	 * Alocira novo polje veličine kolekcije. Popuni polje s elementima kolekcije koje vraća.
	 */
	@Override
	public Object[] toArray() {
		return elements;
	}
	
	/**
	 * Metoda zove processor.process(.) za svaki element kolekcije.
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0 ; i < this.size() ; i++) {
			processor.process(this.get(i));
		}
	}
	
	@Override
	public boolean isEmpty() {
		return this.size==0? true : false;
	}
	
	/**
	 * Udvostručuje kapacitet polja, čuva elemente.
	 */
	public void resize() {
		this.capacity *=2;
		this.elements = Arrays.copyOf(elements, capacity);
	}

}
