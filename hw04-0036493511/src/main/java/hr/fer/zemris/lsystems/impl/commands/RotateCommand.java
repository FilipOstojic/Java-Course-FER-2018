package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji definira komandu za rotaciju vektora smijera kornjače.
 * 
 * @author Filip
 *
 */
public class RotateCommand implements Command {
	/**
	 * Kut rotacije.
	 */
	private double angle;

	/**
	 * Konstruktor, prima kut rotacije.
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Metoda koja rotira vektor smjera kornjače.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}

}
