package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Razred <code>IntegerStorage</code> predstavlja Subjekt poznate terminologije:
 * "Observer design pattern". Sadrži listu registriranih promatrača (razreda
 * koji implementiraju sučelje {@link IntegerStorageObserver}. Promjenu
 * vrijednosti(stanja) javlja svim registriranim promatračima.
 * 
 * @author Filip
 *
 */
public class IntegerStorage {
	/**
	 * Trenutna vrijednost.
	 */
	private int value;
	/**
	 * Lista registriranih promatrača.
	 */
	private List<IntegerStorageObserver> observers;
	/**
	 * Iterator po promatračima.
	 */
	Iterator<IntegerStorageObserver> it;

	/**
	 * Konstruktor, prima inicijalnu vrijednost.
	 * 
	 * @param initialValue
	 *            početna vrijednost
	 */
	public IntegerStorage(int initialValue) {
		observers = new ArrayList<>();
		this.value = initialValue;
	}

	/**
	 * Metoda koja dodaje promatrača u listu, ako već ne postoji.
	 * 
	 * @param observer
	 *            promatrač
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Metoda koja uklanja promatrača iz liste ako takav postoji.
	 * 
	 * @param observer
	 *            promatrač
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			it.remove();
		}
	}

	/**
	 * Metoda briše sve promatrače iz liste.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Metoda vraća trenutno pohranjenu vrijednnost.
	 * 
	 * @return vrijednost pohranjena u Subjektu
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Postavlja novu vrijednost i obavještava sve registrirane promatrače o
	 * promjeni.
	 * 
	 * @param value
	 *            nova vrijednost
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				it = observers.iterator();
				while (it.hasNext()) {
					IntegerStorageObserver observer = it.next();
					observer.valueChanged(this);
				}
			}
		}
	}
}
