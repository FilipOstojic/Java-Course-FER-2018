package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Razred s main metodom za testiranje. Po pokretanju će se dobiti prozor gdje
 * ćete moći mijenjati stupanj i vidjeti generiranu krivulju.
 * 
 * @author Filip
 *
 */
public class Glavni2 {

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}

	/**
	 * Metoda kojom se testira dio kochove pahuljice. Vraća primjerak razreda
	 * LSystem metodom configureFromText(data).
	 * 
	 * @param provider
	 *            LSystemBuilderProvider
	 * @return LSystem
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] { 
				"origin 0.05 0.4", 
				"angle 0", 
				"unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0", 
				"", 
				"command F draw 1", 
				"command + rotate 60",
				"command - rotate -60", 
				"", 
				"axiom F", 
				"", 
				"production F F+F--F+F" };

		return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
