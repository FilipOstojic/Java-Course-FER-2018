package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Razred koji definira komandu za dodavanje na vrh stoga.
 * 
 * @author Filip
 *
 */
public class PushCommand implements Command {

	/**
	 * Metoda koja dodaje stanje na vrh stoga.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtleState = ctx.getCurrentState().copy();
		ctx.pushState(turtleState);
	}

}
