package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Funkcijsko sučelje <code>Command</code>.
 * 
 * @author Filip
 *
 */
public interface Command {
	/**
	 * Metoda koju moraju imati sve naredbe koje nasljeđuju ovo sučelje. Naredba
	 * izvodi određeni zadatak ovisno o komandi.
	 * 
	 * @param ctx
	 *            Context
	 * @param painter
	 *            Painter
	 */
	void execute(Context ctx, Painter painter);
}
