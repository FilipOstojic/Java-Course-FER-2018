package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Razred s main metodom za testiranje. Po pokretanju će se dobiti prozor gdje
 * ćete moći mijenjati stupanj i vidjeti generiranu krivulju.
 * 
 * @author Filip
 *
 */
public class Glavni3 {

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
