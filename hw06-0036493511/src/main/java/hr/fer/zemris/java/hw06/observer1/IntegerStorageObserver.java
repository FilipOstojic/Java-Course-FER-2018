package hr.fer.zemris.java.hw06.observer1;

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
	 * @param istorage
	 *            subjekt
	 */
	public void valueChanged(IntegerStorage istorage);
}
