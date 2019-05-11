package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Predstavlja razred kojim računa gdje kornjača mora otići; povlači liniju
 * zadanom bojom od trenutne pozicije kornjače do izračunate i pamti u trenutnom
 * stanju novu poziciju kornjače.
 * 
 * @author Filip
 *
 */
public class DrawCommand implements Command {
	/**
	 * Pomak.
	 */
	private double step;

	/**
	 * Konstuktor koji prima pomak.
	 * 
	 * @param step
	 *            pomak
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Metoda koja crta liniju zadane boje od zadane pozicije do pomaknute. Pamti
	 * pomaknutu poziciju kao novu trenutnu.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double x0 = ctx.getCurrentState().getCurrentPosition().getX();
		double y0 = ctx.getCurrentState().getCurrentPosition().getY();

		Vector2D offset = ctx.getCurrentState().getDirection().normalize().scaled(step*ctx.getCurrentState().getEffectiveLength());
		ctx.getCurrentState().getCurrentPosition().translate(offset);

		double x1 = ctx.getCurrentState().getCurrentPosition().getX();
		double y1 = ctx.getCurrentState().getCurrentPosition().getY();

		painter.drawLine(x0, y0, x1, y1, ctx.getCurrentState().getColor(), 1);
	}

}
