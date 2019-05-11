package hr.fer.zemris.java.hw06.observer2;

/**
 * Razred <code>IntegerStorageChange</code> enkapsulira
 * <code>IntegerStorage</code> te sprema(ažurira) vrijednosti integera prije i
 * nakon promjene.
 * 
 * @author Filip
 *
 */
public class IntegerStorageChange {
	/**
	 * Trenutna vrijednost u subjektu.
	 */
	private int currentvalue;
	/**
	 * Prošla vrijednost u subjektu.
	 */
	private int previusValue;
	/**
	 * Subjekt.
	 */
	private IntegerStorage iStorage;

	/**
	 * Konstruktor, prima <code>IntegerStorage</code> subjekt.
	 * 
	 * @param iStorage
	 *            subjekt
	 */
	public IntegerStorageChange(IntegerStorage iStorage) {
		super();
		this.iStorage = iStorage;
		this.currentvalue = iStorage.getValue();
	}

	/**
	 * Metoda vraća trenutnu vrijednost u subjektu.
	 * 
	 * @return trenutna vrijednost u subjektu
	 */
	public int getCurrentvalue() {
		currentvalue = iStorage.getValue();
		return currentvalue;
	}

	/**
	 * Metoda vraća prethodnu vrijednost u subjektu.
	 * 
	 * @return prethodna vrijednost u subjektu
	 */
	public int getPreviusValue() {
		previusValue = currentvalue;
		return previusValue;
	}

	/**
	 * Metoda vraća subjekt.
	 * 
	 * @return subjekt
	 */
	public IntegerStorage getiStorage() {
		return iStorage;
	}

}
