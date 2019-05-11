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
public class Glavni1 {

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}

	/**
	 * Metoda kojom se testira dio kochove pahuljice. Vraća primjerak razreda
	 * LSystem metodom build().
	 * 
	 * @param provider
	 *            LSystemBuilderProvider
	 * @return LSystem
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4).setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0 / 3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}
}
