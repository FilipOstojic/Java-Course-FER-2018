package hr.fer.zemris.java.hw06.observer2;

/**
 * Promatrač <code>ChangeCounter</code> broji i na standardni izlaz ispisuje
 * broj promjena vrijednosti subjekta od svoje inicijalizacije.
 * 
 * @author Filip
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Broj promjena vrijednosti subjekta.
	 */
	private int changed;

	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Number of value changes since tracking: " + (++changed));
	}

	/**
	 * Metoda vraća broj promjena subjekta.
	 * 
	 * @return broj promjena subjekta
	 */
	public int getChanged() {
		return changed;
	}

}
