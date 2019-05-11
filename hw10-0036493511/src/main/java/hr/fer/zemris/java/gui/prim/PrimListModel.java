package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Razred <code>PrimListModel</code> predstavlja model s dvije liste te
 * mogućnost unosa prostih brojeva u njih koristeći {@link ListModel}.
 * 
 * @author Filip
 * @param <T>
 *            parametar
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * Lista elemenata.
	 */
	private List<Integer> elementi;
	/**
	 * Lista promatrača.
	 */
	private List<ListDataListener> promatraci;
	/**
	 * Početna vrijednost.
	 */
	private Integer lastPrime = 1;
	
	/**
	 * Konstruktor.
	 */
	public PrimListModel() {
		elementi = new ArrayList<>();
		promatraci = new ArrayList<>();
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}

	@Override
	public int getSize() {
		return elementi.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elementi.get(index);
	}

	/**
	 * Metoda dodaje element u listu.
	 * 
	 * @param element
	 *            element
	 */
	public void add(Integer element) {
		int pos = elementi.size();
		elementi.add(element);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : promatraci) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Metoda vraća sljedeći prosti broj.
	 * 
	 * @return sljedeći prosti broj
	 */
	public Integer next() {
		lastPrime = getLastPrime();
		int brojac = 0;
		for (int i = lastPrime + 1;; i++) {
			for (int j = 1; j <= i; j++) {
				if (i % j == 0) {
					brojac++;
				}
			}
			if (brojac == 2) {
				lastPrime = i;
				brojac = 0;
				add(lastPrime);
				return lastPrime;
			}
			brojac = 0;
		}
	}

	/**
	 * Metoda vraća posljednji prosti broj ili 1 ako nije unesen niti jedan prosti
	 * broj.
	 * 
	 * @return zadnji prosti broj
	 */
	private Integer getLastPrime() {
		return lastPrime == 1 ? lastPrime : (Integer) getElementAt(getSize() - 1);
	}
}
