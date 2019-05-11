package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Razred <code>TurtleState</code> definira stanje kornjače (trenutnu poziciju,
 * smjer, boju crte te efektivnu duljinu pomaka).
 * 
 * @author Filip
 *
 */
public class TurtleState {
	/**
	 * Trenutna pozicija kornjače.
	 */
	private Vector2D currentPosition;
	/**
	 * Smjer/orjentacija kornjače.
	 */
	private Vector2D direction;
	/**
	 * Boja linije.
	 */
	private Color color;
	/**
	 * Efektivna duljina pomaka.
	 */
	private double effectiveLength;

	/**
	 * Konstruktor.
	 * 
	 * @param currentPosition
	 *            trenutna pozicija
	 * @param direction
	 *            smjer
	 * @param color
	 *            boja crte
	 * @param effectiveLength
	 *            efektivna duljina pomaka
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double effectiveLength) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.effectiveLength = effectiveLength;
	}

	/**
	 * Metoda stavara kopiju trenutnog stanja.
	 * 
	 * @return kopiju stanja
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), direction.copy(), color, effectiveLength);
	}

	/**
	 * Metoda vraća trenutnu poziciju kornjače.
	 * 
	 * @return radijvektor do trenutne pozicije
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Metoda vraća trenutni smjer kornjače.
	 * 
	 * @return trenutni smjer
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Metoda vraća boju linije.
	 * 
	 * @return boja linije
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Metoda vraća efektivnu duljinu pomaka kornjače.
	 * 
	 * @return efektivna duljina pomaka kornjače
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

	/**
	 * Metoda postavlja trenutnu poziciju kornjače.
	 * 
	 * @param currentPosition
	 *            trenutna pozicija
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Metoda postavlja trenutni smjer kornjače.
	 * 
	 * @param direction
	 *            trenutni smjer
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Metoda postavlja boju linije.
	 * 
	 * @param color
	 *            boja linije
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Metoda postavlja efektivnu duljinu pomaka kornjače.
	 * 
	 * @param effectiveLength
	 *            efektivna duljina pomaka kornjače
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}

}
