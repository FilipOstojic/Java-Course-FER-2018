package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * Razred <code>PrimesCollection</code> predstavlja kolekciju prostih brojeva.
 * Sadrži n prostih brojeva od broja dva pa nadalje.
 * 
 * @author Filip
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Broj prostih brojeva koje kolekcija sadrži.
	 */
	private int noOfPrimes;

	/**
	 * Konstruktor, prima željeni broj prostih brojeva.
	 * 
	 * @param noOfPrimes
	 *            željeni broj prostih brojeva
	 */
	public PrimesCollection(int noOfPrimes) {
		this.noOfPrimes = noOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator(noOfPrimes);
	}

	/**
	 * Razred <code>PrimesIterator</code> predstalja iterator po kolekciji prostih
	 * brojeva.
	 * 
	 * @author Filip
	 *
	 */
	public class PrimesIterator implements Iterator<Integer> {
		/**
		 * Brojač brojava, koji dijeljenjem daju ostatak 0.
		 */
		int brojac = 0;
		/**
		 * Varijabla pamti zadnji prosti broj koji je izračunala.
		 */
		private int lastPrime = 1;
		/**
		 * Ukupan broj prostih brojeva.
		 */
		int noOfPrimes;

		/**
		 * Konstruktor, prima broj prostih brojeva koje će generirati.
		 * 
		 * @param noOfPrimes
		 *            broj prostih brojeva
		 */
		public PrimesIterator(int noOfPrimes) {
			this.noOfPrimes = noOfPrimes;
		}

		/**
		 * Metoda gleda ima li još prostih brojeva za izgenerirati.
		 */
		@Override
		public boolean hasNext() {
			if (noOfPrimes > 0) {
				noOfPrimes--;
				return true;
			}
			return false;
		}

		/**
		 * Metoda vraća sljedeći prosti broj.
		 */
		@Override
		public Integer next() {
			for (int i = lastPrime + 1;; i++) {
				for (int j = 1; j <= i; j++) {
					if (i % j == 0) {
						brojac++;
					}
				}
				if (brojac == 2) {
					lastPrime = i;
					brojac = 0;
					return lastPrime;
				}
				brojac = 0;
			}
		}

	}

}
