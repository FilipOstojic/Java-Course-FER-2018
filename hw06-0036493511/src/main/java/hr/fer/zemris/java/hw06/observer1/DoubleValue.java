package hr.fer.zemris.java.hw06.observer1;

/**
 * Promatač <code>DoubleValue</code> ispisuje na standardni izlaz dvostruku
 * vrijednost od one u subjektu, ali samo prvih n puta od inicijalizacije, nakon
 * čega će se de-regisrirati iz tog subjekta.
 * 
 * @author Filip
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Varijabla koja čuva broj preostalih ispisa koje će promatrač napraviti.
	 */
	private int repeat;

	/**
	 * Konstruktor, prima ukupni broj ispisa koje će promatrač obaviti.
	 * 
	 * @param noOfRepeats
	 *            ukupni broj ispisa koje će promatrač obaviti
	 */
	public DoubleValue(int noOfRepeats) {
		repeat = noOfRepeats;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if ((repeat--) > 0) {
			System.out.format("Double value: %d \n", istorage.getValue() * 2);
		} else {
			istorage.removeObserver(this);
		}
	}

}
