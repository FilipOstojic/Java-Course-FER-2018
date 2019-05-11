package hr.fer.zemris.java.hw06.observer1;

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
	private int change;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking: " + (++change));
	}

	/**
	 * Metoda vraća broj promjena subjekta.
	 * 
	 * @return broj promjena subjekta
	 */
	public int getChange() {
		return change;
	}

}
