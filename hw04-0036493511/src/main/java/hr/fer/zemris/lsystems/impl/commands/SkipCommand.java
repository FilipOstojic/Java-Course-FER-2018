package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Razred koji predstavlja komandu za pomak kornjače.
 * 
 * @author Filip
 *
 */
public class SkipCommand implements Command {
	/**
	 * Pomak.
	 */
	private double step;

	/**
	 * Konstruktor, prima pomak.
	 * 
	 * @param step
	 *            pomak
	 */
	public SkipCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Metoda koja pomiče kornjaču, bez iscrtavanja linija.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D offset = ctx.getCurrentState().getDirection().scaled(step).scaled(ctx.getCurrentState().getEffectiveLength());
		ctx.getCurrentState().getCurrentPosition().translate(offset);
	}

}
