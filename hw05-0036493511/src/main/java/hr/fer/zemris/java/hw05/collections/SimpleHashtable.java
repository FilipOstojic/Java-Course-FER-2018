package hr.fer.zemris.java.hw05.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

/**
 * Ova kolekcija predstalja tablicu raspršenog adresiranja koja čuva zapise
 * oblika ključ-vrijednost koji su primjerci razreda <code>TableEntry</code>.
 * Ključ ne smije biti null referenca, dok vrijednost smije. Ključ je
 * parametriziran tipom K, a vrijednost tipom V.
 * 
 * @author Filip
 *
 * @param <K>
 *            Key -tip ključa
 * @param <V>
 *            Value -tip vrijednosti
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Varijabla čuva veličinu kolekcije.
	 */
	private int size;
	/**
	 * Polje primjeraka razreda <code>TableEntry</code> .
	 */
	private TableEntry<K, V>[] array;
	/**
	 * Brojač svih promjena kolekcije od njene inicijalizacije. Promjene su:
	 * dodavanje ili brisanje člana kolekcije.
	 */
	private int modificationCount;
	/**
	 * Konstanta za pretpostavljenu veličinu kolekcije.
	 */
	private static final int DEFAULTCAPACITY = 16;

	/**
	 * Defaultni konstruktor, inicijalizira veličinu kolekcije na 16.
	 */
	public SimpleHashtable() {
		this(DEFAULTCAPACITY);
	}

	/**
	 * Konstruktor, prima veličinu kolekcije koja se skalira na prvu sljedeću
	 * potenciju broja 2 veću ili jednaku zadanom broju.
	 * 
	 * @param capacity
	 *            -željena veličina kolekcije
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Minimum capacity is 1, was: " + capacity);
		array = (TableEntry<K, V>[]) new TableEntry[calculateCapacity(capacity)];
	}

	/**
	 * Metoda računa prvu potenciju broja 2 koja je veća ili jednaka od željenog
	 * broja.
	 * 
	 * @param capacity
	 *            -željeni broj
	 * @return prvu potenciju broja 2 veću ili jednaku željenom broju
	 */
	private int calculateCapacity(int capacity) {
		int result = 1;
		do {
			result *= 2;
			capacity = (int) Math.ceil(capacity / 2.0);
		} while (capacity > 1);
		return result;
	}

	/**
	 * Pomoćni razred <code>TableEntry</code> koji čuva ključ i vrijednost.
	 * 
	 * @author Filip
	 *
	 * @param <K>
	 *            tip ključa
	 * @param <V>
	 *            tip vrijednosti
	 */
	public static class TableEntry<K, V> {
		/**
		 * Varijabla koja čuva ključ.
		 */
		private K key;
		/**
		 * Varijabla koja čuva vrijednost.
		 */
		private V value;
		/**
		 * Pokazivač na sljedeći element u istom redu tablice.
		 */
		private TableEntry<K, V> next;

		/**
		 * Konstruktor prima ključ, vrijednost i sljedeći element ako postoji, inače je
		 * null.
		 * 
		 * @param key
		 *            -ključ
		 * @param value
		 *            -vrijednost
		 * @param next
		 *            -sljedeći element, ako ne postoji onda je null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Metoda vraća vrijednost ključa.
		 * 
		 * @return vrijednost ključa
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Metoda vraća vrijednost.
		 * 
		 * @return vrijednost
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Metoda postavlja vrijednost.
		 * 
		 * @param value
		 *            vrijednost
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode(java.lang.Object)
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
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
			TableEntry<?, ?> other = (TableEntry<?, ?>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString(java.lang.Object)
		 */
		@Override
		public String toString() {
			return String.format("%s=%s", key, value);
		}
	}

	/**
	 * Metoda stavlja novi član u kolekciju, ako njegov ključ već ne postoji, inače
	 * ažurira staru vrijednost na novu (onog elementa koji ima isti ključ).
	 * Elemente dodaje po principu raspršenog adresiranja vrijednosti ključa u novi
	 * slot, ako je prazan ili na kraj liste, ako je neki element već u tom slotu.
	 * 
	 * @param key
	 *            -ključ
	 * @param value
	 *            -vrijednost
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("The key should not be null.");
		if (this.containsKey(key)) {
			TableEntry<K, V> entry = getEntry(key);
			entry.setValue(value);
			return;
		}
		if (array[index(key)] == null) {
			array[index(key)] = new TableEntry<K, V>(key, value, null);
			size++;
			modificationCount++;
			culculatePercentage();
			return;
		}
		TableEntry<K, V> entry = array[index(key)];
		while (entry.next != null) {
			entry = entry.next;
		}
		entry.next = new TableEntry<K, V>(key, value, null);
		size++;
		modificationCount++;
	}

	/**
	 * Metoda računa popunjenost tablice. Popunjenost je definirana omjerom ukupnog
	 * broja elemenata i veličine tablice.
	 */
	private void culculatePercentage() {
		if ((double) size / array.length >= 0.75) {
			resize();
		}
	}

	/**
	 * Metoda povećava trenutnu veličinu tablice za duplo. Pritom ponovno vrši
	 * alokaciju elemenata koji se u njoj nalaze.
	 */
	private void resize() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newArray = (TableEntry<K, V>[]) new TableEntry[array.length * 2];
		for (TableEntry<K, V> entry : array) {
			do {
				if (entry == null)
					break;
				TableEntry<K, V> entryNew = new TableEntry<K, V>(entry.key, entry.value, null);
				int index = (Math.abs(entryNew.key.hashCode())) % newArray.length;
				if (newArray[index] == null) {
					newArray[index] = entryNew;
				} else {
					TableEntry<K, V> entryTmp = newArray[index];
					while (entryTmp.next != null) {
						entryTmp = entryTmp.next;
					}
					entryTmp.next = entryNew;
				}
				entry = entry.next;
			} while (entry != null);
		}
		array = Arrays.copyOf(newArray, newArray.length);
	}

	/**
	 * Metoda briše sve elemente u kolekciji.
	 */
	public void clear() {
		for (TableEntry<K, V> entry : array) {
			while (entry != null) {
				remove(entry.key);
				entry = entry.next;
			}
		}
		size = 0;
	}

	/**
	 * Pomoćna metoda koja računa indeks koji bi određeni ključ ima u tablici ovisno
	 * o njenoj veličini.
	 * 
	 * @param key
	 *            ključ
	 * @return ideks u tablici kojeg bi imao ključ
	 */
	private int index(Object key) {
		return (Math.abs(key.hashCode())) % array.length;
	}

	/**
	 * Pomoćna metoda koja vraća primjerak razreda <code>TableEntry</code> koji
	 * sadrži uneseni ključ. Ako takav ne postoji vraća iznimku.
	 * 
	 * @param key
	 *            ključ
	 * @return primjerak razreda <code>TableEntry</code> koji sadrži ključ
	 */
	private TableEntry<K, V> getEntry(Object key) {
		int index = index(key);
		if (array[index] != null) {
			TableEntry<K, V> entry = array[index];
			do {
				if (entry.getKey().equals(key))
					return entry;
				entry = entry.next;
			} while (entry != null);
		}
		throw new NoSuchElementException("The key does not exists: " + key);
	}

	/**
	 * Metoda vraća vrijednost elementa čiji je ključ primila ili baca iznimku ako
	 * ne postoji element sa zadanim ključem.
	 * 
	 * @param key
	 *            ključ (ne smije biti null)
	 * @return vrijednost elementa
	 */
	public V get(Object key) {
		if (key == null)
			throw new NullPointerException("The key should not be null.");
		try {
			TableEntry<K, V> entry = getEntry(key);
			return (V) entry.value;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("The key does not exists: " + key);
		}
	}

	/**
	 * Metoda vraća veličinu kolekcije.
	 * 
	 * @return veličina kolekcije
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Metoda provjera sadrži li kolekcija element s određenim ključem.
	 * 
	 * @param key
	 *            ključ
	 * @return true ako kolekcija sadrži element s određenim ključem, inače false
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			throw new NullPointerException("The key should not be null.");
		try {
			getEntry(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metoda provjera sadrži li kolekcija element s određenom vrijednosti.
	 * 
	 * @param value
	 *            vrijednost
	 * @return true ako kolekcija sadrži element s određenom vrijednosti, inače
	 *         false
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < array.length; i++) {
			TableEntry<K, V> entry = array[i];
			if (entry == null)
				continue;
			do {
				if (entry.getValue().equals(value))
					return true;
				entry = entry.next;
			} while (entry != null);
		}
		return false;
	}

	/**
	 * Metoda uklanja element iz kolekcije koji sadrži zadani ključ. Ako takav
	 * element ne postoji ne javlja grešku.
	 * 
	 * @param key
	 *            ključ
	 */
	public void remove(Object key) {
		try {
			TableEntry<K, V> entry = getEntry(key);
			modificationCount++;
			if (array[index(key)] == entry) {
				array[index(key)] = entry.next;
				entry = null;
				size--;
			} else if (entry.next == null) {
				entry = null;
				size--;
			} else {
				TableEntry<K, V> entryPrevious = array[index(key)];
				while (entryPrevious.next != entry) {
					entryPrevious = entryPrevious.next;
				}
				entryPrevious.next = entry.next;
				entry = null;
				size--;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Metoda provjerava je li kolekcija prazna.
	 * 
	 * @return true ak je kolekcija prazna, inače false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Metoda vraća broj slotova u tablici.
	 * 
	 * @return broj slotova tablice
	 */
	public int getLentgth() {
		return array.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "[", "]");
		for (TableEntry<K, V> entry : array) {
			if (entry == null)
				continue;
			do {
				sj.add(entry.toString());
				entry = entry.next;
			} while (entry != null);
		}
		return sj.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Collection#Iterator(java.lang.Collection)
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Razred <code>IteratorImpl</code> predstavlja iterator koji je napravljen za
	 * iteriranje po primjercima razreda <code>SimpleHashtable</code>.
	 * 
	 * @author Filip
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Varijabla pamti trenutni indeks slota tablice u kojem se nalazi trenutni
		 * element.
		 */
		private int currentSlot = 0;
		/**
		 * Trenutni element.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Varijabla pamti broj promjena kolekcije.
		 */
		private int modificationCountCopy = modificationCount;
		/**
		 * Zastavica, metoda remove je postavlja na true, a metoda next na false.
		 */
		private boolean nextCalled = false;

		/**
		 * Metoda provjerava postoji li element u tablici nakon trenutnog.
		 */
		public boolean hasNext() {
			if (modificationCountCopy != modificationCount)
				throw new ConcurrentModificationException("You shall not modify collection without using Iterator.");
			if (currentEntry == null && currentSlot == 0) {
				for (TableEntry<K, V> entry : array) {
					if (entry != null)
						return true;
				}
				return false;
			}
			if (currentEntry == null)
				return false;
			if (currentEntry.next != null)
				return true;
			for (int i = currentSlot + 1; i < array.length; i++) {
				if (array[i] != null)
					return true;
			}
			if (!nextCalled)
				return true;
			return false;
		}

		/**
		 * Metoda vraća sljedeći element ako postoji, inače baca iznimku.
		 */
		public TableEntry<K, V> next() {
			if (modificationCountCopy != modificationCount)
				throw new ConcurrentModificationException("You shall not modify collection without using Iterator.");
			if (currentEntry == null && currentSlot == 0) {
				for (TableEntry<K, V> entry : array) {
					if (entry != null) {
						currentEntry = entry;
						currentSlot = index(entry.key);
						nextCalled = true;
						return currentEntry;
					}
				}
				throw new NoSuchElementException("SimpleHashtable is empty.");
			} else if (currentEntry == null) {
				throw new NoSuchElementException("SimpleHashtable has no more elements.");
			}
			if (!nextCalled) {
				nextCalled = true;
				return currentEntry;
			}
			if (currentEntry.next != null) {
				currentEntry = currentEntry.next;
				nextCalled = true;
				return currentEntry;
			}
			for (int i = currentSlot + 1; i < array.length; i++) {
				if (array[i] != null) {
					currentEntry = array[i];
					currentSlot = i;
					nextCalled = true;
					return currentEntry;
				}
			}
			throw new NoSuchElementException("SimpleHashtable has no more elements.");
		}

		/**
		 * Metoda briše trenutni član kolekcije i ažurira trenutni član kolekcije na
		 * sljedeći ako postoji inače na null.
		 */
		public void remove() {
			if (modificationCountCopy != modificationCount)
				throw new ConcurrentModificationException("You shall not modify collection without using Iterator.");
			if (!nextCalled) {
				throw new IllegalStateException(
						"The next method has not yet been called, or the remove method has already been called after the last call to the next method");
			}
			if (currentSlot == array.length) {
				throw new IllegalStateException("There is no more elements to remove.");
			}
			if (currentEntry.next != null) {
				TableEntry<K, V> currentEntryNext = currentEntry.next;
				SimpleHashtable.this.remove(currentEntry.key);
				modificationCountCopy = ++modificationCount;
				// ni slot ni pozicija u slotu se ne mijenjaju
				currentEntry = currentEntryNext;
			} else {
				TableEntry<K, V> currentEntryNext = null;
				for (int i = currentSlot + 1; i < array.length; i++) {
					if (array[i] != null) {
						currentEntryNext = array[i];
						currentSlot = i;
						break;
					}
				}
				if (currentEntryNext == null) {
					SimpleHashtable.this.remove(currentEntry.key);
					currentEntry = null; // nema više elemenata
					currentSlot = array.length; // izvan polja
				} else {
					SimpleHashtable.this.remove(currentEntry.key);
					currentEntry = currentEntryNext;
				}
				modificationCountCopy = ++modificationCount;
			}
			nextCalled = false;
		}
	}
}
