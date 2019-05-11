package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji definira naredbu koja miče vrijednost s vrha stoga.
 * 
 * @author Filip
 *
 */
public class PopCommand implements Command {

	/**
	 * Metoda miče vrijednost s vrha stoga.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
