package hr.fer.zemris.java.hw06.observer2;

/**
 * <code>IntegerStorageObserver</code> je funkcijsko sučelje koje pramatrači
 * moraju implementirati.
 * 
 * @author Filip
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Metoda koja radi neku obradu nove vrijednosti.
	 * 
	 * @param IntegerStorageChange
	 *            primjerak razreda koji enkapsulira subjekt
	 */
	public void valueChanged(IntegerStorageChange integerStorageChange);
}
