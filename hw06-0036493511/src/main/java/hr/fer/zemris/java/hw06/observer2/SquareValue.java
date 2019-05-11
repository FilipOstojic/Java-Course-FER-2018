package hr.fer.zemris.java.hw06.observer2;

/**
 * Promatrač <code>SquareValue</code> na standardni izlaz ispisuje vrijednost
 * dvostruku od integera koji je spremljen u subjektu.
 * 
 * @author Filip
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.format("Provided new value: %d, square is %.0f \n", change.getCurrentvalue(),
				Math.pow(change.getCurrentvalue(), 2));
	}

}
