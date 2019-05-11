package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji predstavlja komandu za skaliranje vektora.
 * 
 * @author Filip
 *
 */
public class ScaleCommand implements Command {
	/**
	 * Faktor skaliranja.
	 */
	private double factor;

	/**
	 * Konstruktor, prima faktor.
	 * 
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}

	/**
	 * Metoda koja skalira korak kornjače.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double newEffectiveLength = ctx.getCurrentState().getEffectiveLength() * factor;
		ctx.getCurrentState().setEffectiveLength(newEffectiveLength);
	}

}
