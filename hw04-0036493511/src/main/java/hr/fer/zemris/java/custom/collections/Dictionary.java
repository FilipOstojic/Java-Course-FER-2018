package hr.fer.zemris.java.custom.collections;

/**
 * Razred <code>Dictionary</code> predstavlja kolekciju Objekata po principu
 * ključ-vrijednost. Svakom ključu je pridružena njegova vrijednost. Razred ne
 * može sadržavati više istih ključeva niti oni mogu biti null reference (kao skup).
 * Vrijednosti se mogu ponavljati i mogu biti null reference.
 * 
 * @author Filip
 *
 */
public class Dictionary {

	/**
	 * Kolekcija objekata koju koristi <code>Dictionary</code> za pohranu elemenata
	 * tipa <code>Entry</code>.
	 */
	private ArrayIndexedCollection array;

	/**
	 * Defaultni konstruktor.
	 */
	public Dictionary() {
		array = new ArrayIndexedCollection();
	}

	/**
	 * Metoda ispituje je li riječnik prazan.
	 * 
	 * @return true ako je prazan, inače false.
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}

	/**
	 * Metoda vraća veličinu riječnika.
	 * 
	 * @return veličina riječnika
	 */
	public int size() {
		return array.size();
	}

	/**
	 * Metoda koja briše sve elemente riječnika.
	 */
	public void clear() {
		array.clear();
	}

	/**
	 * Meotda stavlja u riječnik po principu: ako uneseni ključ ne postoji zasad u
	 * riječniku on se dodaje skupa s pridruženom vrijednosti. Ako postoji novi unos
	 * će pregaziti staru vrijednost.
	 * 
	 * @param key
	 *            ne smije biti null referenca.
	 * @param value
	 *            smije biti null referenca.
	 */
	public void put(Object key, Object value) {
		if (key == null)
			throw new NullPointerException("The key should not be null reference.");
		Entry entry = new Entry(key, value);
		if (array.contains(entry)) {
			int index = array.indexOf(entry);
			((Entry) array.get(index)).setValue(value);
		} else {
			array.add(entry);
		}
	}

	/**
	 * Metoda vraća vrijednost unesenog ključa, ako takva postoji. Inače vraća null.
	 * 
	 * @param key
	 *            ključ
	 * @return vrijednost ključa ili null referenca
	 */
	public Object get(Object key) {
		Entry entry = new Entry(key, null);
		if (array.contains(entry)) {
			return ((Entry) array.get(array.indexOf(entry))).getValue();
		} else {
			return null;
		}
	}

	/**
	 * Ugnježđeni razred <code>Entry</code> pohranjuje ključ i vrijednost.
	 * 
	 * @author Filip
	 *
	 */
	private static class Entry {
		/**
		 * Ključ pod kojim se čuva vrijednost.
		 */
		private Object key;
		/**
		 * Vrijednost.
		 */
		private Object value;

		/**
		 * Konstuktor koji prima ključ i vrijednost.
		 * 
		 * @param key
		 *            Ključ.
		 * @param value
		 *            Vrijednost.
		 */
		public Entry(Object key, Object value) {
			if (key == null) {
				throw new NullPointerException("The key should not be null. ");
			}
			this.key = key;
			this.value = value;
		}

		/**
		 * Metoda vraća vrijednost <code>Entry</code>-a.
		 * 
		 * @return vrijednost
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Metoda postavlja vrijednost <code>Entry</code>-a.
		 * 
		 */
		public void setValue(Object value) {
			this.value = value;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (this.key == null) {
				if (other.key != null)
					return false;
			} else if (!this.key.equals(other.key))
				return false;
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.size(); i++) {
			sb.append(String.format("[%s,%s]\n", ((Entry) array.get(i)).key, ((Entry) array.get(i)).value));
		}
		return sb.toString();
	}
}
