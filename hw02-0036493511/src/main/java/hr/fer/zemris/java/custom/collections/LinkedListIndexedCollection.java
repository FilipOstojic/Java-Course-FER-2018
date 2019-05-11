package hr.fer.zemris.java.custom.collections;

/**
 * Razred <code>LinkedListIndexedCollection</code> predstavlja implementaciju kolekcije
 * koja se temelji na dvostruko povezanoj listi. Duplikati su dozvoljeni, a null vrijednosti nisu.
 * @author Filip Ostojić
 *
 */
public class LinkedListIndexedCollection extends Collection {
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Konstruktor koji stavara praznu kolekciju.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = last = null;
	}
	
	/**
	 * Konstruktor koji stvara novu kolekciju i puni je s elementima
	 * druge kolekcije ako takvi postoje.
	 * @param col (Collection)
	 */
	public LinkedListIndexedCollection(Collection col) {
		if (col == null) {
			throw new NullPointerException("The collection should not be null.");
		}
		if (col instanceof LinkedListIndexedCollection) {
			for (int i = 0 ; i < col.size() ; i++ ) {
				this.add(((LinkedListIndexedCollection) col).get(i)); 
			}
		} else if (col instanceof ArrayIndexedCollection){
			for (int i = 0 ; i < col.size() ; i++ ) {
				this.add(((ArrayIndexedCollection) col).get(i)); 
			}
		}
	}
	
	/**
	 * Metoda dodaje čvor na kraj liste. Novi element postaje element s najvećim indexom.
	 * Složenost metode je O(1).
	 */
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("The value should not be null.");
		}
		ListNode newNode = new ListNode(value);
		if (this.size() == 0) {
			this.first = newNode;
			this.last = newNode;
		} else {
			this.last.next = newNode;
			newNode.previous = this.last;
			this.last = newNode;
		}
		this.size++;
	}
	
	/**
	 * Vraća objekt koji je smješten u listi na mjestu index. Vrijednost indexa
	 * iz intervala [0,size-1]. Složenost nije veća od O(n/2+1)
	 * @param index
	 * @return objekt na mjestu index ili iznimku ukoliku je index iz nedozvojenog područja
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size()-1) {
			throw new IllegalArgumentException("Index should be between 0 and "+(this.size()-1));
		}
		ListNode tmpNode;
		if (index < this.size()/2) {
			tmpNode = this.first;
			for (int i = 0; i<index ; i++) {
				tmpNode = tmpNode.next;
			}
		} else {
			tmpNode = this.last;
			for (int i = this.size()-1 ; i>index ; i--) {
				tmpNode = tmpNode.previous;
			}
		}
		return tmpNode.value;
	}
	
	/**
	 * Briše sve elemente kolekcije.
	 */
	@Override
	public void clear() {
		this.size = 0;
		this.first = this.last = null;
	}
	
	/**
	 * Umeće novi čvor na određenu poziciju u listi. Složenost je O(n).
	 * 
	 * @param value (Object)
	 * @param position broj iz intervala od 0 do size
	 */
	public void insert(Object value, int position) {
		if (position<0 || position > this.size()) {
			throw new IllegalArgumentException("Position should be between 0 and "+this.size());
		} else if (position == this.size) {
			this.add(value);
		} else {
			ListNode newNode = new ListNode(value);
			ListNode tmpNode = this.first;
			for(int i = 0 ; i < position ; i++) {
				tmpNode = tmpNode.next;
			}
			newNode.next = tmpNode;
			newNode.previous=tmpNode.previous;
			tmpNode.previous.next=newNode;
			tmpNode.previous=newNode;
		}
	}
	
	/**
	 * Pretražuje kolekciju i vraća poziciju prvog pojavljivanja traženog elementa u listi.
	 * Složenost je O(n).
	 * @param value
	 * @return poziciju u listi zadanog elementa ili -1 ako elementa nema u kolekciji
	 */
	public int indexOf(Object value) {
		if (value==null) {
			throw new NullPointerException("This element is not found.");
		}
		int index=0;	
		ListNode tmpNode = this.first;
		for(int i = 0 ; i < this.size() ; i++) {
			if (tmpNode.value.equals(value)) {
				return index;
			}
			tmpNode = tmpNode.next;
			index++;
		}
		return -1;
	}
	
	/**
	 * Uklanja element na poziciji index iz liste. Ostali elementi se također pomiču
	 * za jedno mjesto nazad.
	 * @param index broj u intervalu od 0 do size-1
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size()-1) {
			throw new IllegalArgumentException("Index should be between 0 and "+(this.size()-1));
		}
		if (index == 0) {
			ListNode tmpNode = this.first.next;
			if(tmpNode != null) {
				tmpNode.previous = null;
				this.first = tmpNode;
			} else {
				this.first = null;
			}
		} else if (index == this.size()-1) {
			ListNode tmpNode = this.last.previous;
			tmpNode.next = null;
			this.last=tmpNode;
		} else {
			ListNode tmpNode = this.first;
			for (int i = 0 ; i < index ; i++) {
				tmpNode = tmpNode.next;
			}
			tmpNode.previous.next=tmpNode.next;
			tmpNode.next.previous=tmpNode.previous;
			tmpNode=null;
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
		int index = this.indexOf(value);
		if(index==-1) return false;
		return true;
	}
	
	/**
	 * Alocira novo polje veličine kolekcije. Popuni polje s elementima kolekcije koje vraća.
	 */
	@Override
	public Object[] toArray() {
		Object [] array = new Object[this.size()];
		ListNode tmpNode = this.first;
		for (int i = 0 ; i < this.size() ; i++) {
			array[i] = tmpNode.value;
			tmpNode = tmpNode.next;
		}
		return array;
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
	
	/**
	 * Metoda zove processor.process(.) za svaki element kolekcije.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean isEmpty() {
		return this.size()==0? true : false;
	}
	
	/**
	 * Statički razred kolji predstavlja čvor liste.
	 * Ima tri varijable: previous, next i value.
	 * @author Filip Ostojić
	 *
	 */
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object value;
		
		/**
		 * Konstruktor čvora.
		 * @param value
		 */
		public ListNode(Object value) {
			if(value == null) {
				throw new NullPointerException("Value should not be null.");
			}
			this.value = value;
			this.next = null;
			this.previous = null;
		}
	}
}
