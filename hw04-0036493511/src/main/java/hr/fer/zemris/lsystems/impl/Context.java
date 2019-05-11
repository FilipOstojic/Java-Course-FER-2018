package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Razred <code>Context</code> predstavlja stog sa stanjima razreda
 * <code>TurtleState</code>. Trenutno stanje se nalazi na vrhu stoga.
 * 
 * @author Filip
 *
 */
public class Context {
	/**
	 * Stog za čuvanje stanja.
	 */
	private ObjectStack stack = new ObjectStack();

	/**
	 * Metoda koja vraća stanje s vrha stoga,ali ga ne miče.
	 * 
	 * @return TurtleState
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}

	/**
	 * Metoda koja stavlja stanje na stog.
	 * 
	 * @param state
	 *            TurtleState
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Metoda miče zadnje stanje s vrha stoga.
	 */
	public void popState() {
		stack.pop();
	}
}
