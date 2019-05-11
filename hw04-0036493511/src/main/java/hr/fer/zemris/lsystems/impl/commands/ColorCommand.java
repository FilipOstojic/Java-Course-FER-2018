package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Predstavlja razred čija metoda postavlja boju u određenom stanju kornjače.
 * 
 * @author Filip
 *
 */
public class ColorCommand implements Command {
	/**
	 * Boja.
	 */
	Color color;

	/**
	 * Konstruktor koji prima boju.
	 * 
	 * @param color
	 *            boja
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}

	/**
	 * Metoda kojom se postavlja boja u određenom stanju kornjače.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
