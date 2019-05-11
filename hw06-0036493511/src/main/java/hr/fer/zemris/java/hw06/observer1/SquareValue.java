package hr.fer.zemris.java.hw06.observer1;

/**
 * Promatrač <code>SquareValue</code> na standardni izlaz ispisuje vrijednost
 * dvostruku od integera koji je spremljen u subjektu.
 * 
 * @author Filip
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.format("Provided new value: %d, square is %.0f \n", istorage.getValue(),
				Math.pow(istorage.getValue(), 2));
	}

}
