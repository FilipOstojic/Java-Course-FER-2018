package hr.fer.zemris.math;

import static java.lang.Math.*;
import java.lang.Math;

/**
 * Razred <code>Vector2D</code> predstavlja vektor u 2D prostoru te definira
 * neke osnovne operacije nad njim (tracija, rotacija itd.).
 * 
 * @author Filip
 *
 */
public class Vector2D {
	/**
	 * Konstanta za 360° punog kruga.
	 */
	private static final double MAXDEGREES = 360.0;
	/**
	 * Konstanta koja definira minimalnu razliku dvije vrijednosti, a da se smatraju
	 * jednakima.
	 */
	private static final double THRESHOLD = 0.0001;
	/**
	 * Realni dio vektora.
	 */
	private double x;
	/**
	 * Imaginarni dio vektora.
	 */
	private double y;

	/**
	 * Konstruktor koji prima realni i imaginarni dio vektora.
	 * 
	 * @param x
	 *            realni dio
	 * @param y
	 *            imaginarni dio
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Metoda koja vraća realni dio vektora.
	 * 
	 * @return realni dio
	 */
	public double getX() {
		return x;
	}

	/**
	 * Metoda koja vraća imaginarni dio vektora.
	 * 
	 * @return imaginarni dio
	 */
	public double getY() {
		return y;
	}

	/**
	 * Metoda koja translantira trenutni vektor.
	 * 
	 * @param offset
	 *            vektor pomaka
	 */
	public void translate(Vector2D offset) {
		this.x = this.x + offset.x;
		this.y = this.y + offset.y;
	}

	/**
	 * Metoda koja vraća novi vektor koji je translantirani trenutni vektor.
	 * 
	 * @param offset
	 *            vektor pomaka
	 * @return translantirani vektor
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D newVector = this;
		newVector.translate(offset);
		return newVector;
	}

	/**
	 * Metoda koja rotira trenutni vektor.
	 * 
	 * @param angle
	 *            kut u stupnjevima
	 */
	public void rotate(double angle) {
		while (angle < 0) {
			angle += MAXDEGREES;
		}
		double radiansRotation = Math.toRadians(angle % MAXDEGREES);
		double magnitude = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
		double radians = Math.atan2(this.y, this.x);

		double xNew = magnitude * cos(radians + radiansRotation);
		double yNew = magnitude * sin(radians + radiansRotation);
		this.x = xNew;
		this.y = yNew;
	}

	/**
	 * Metoda koja vraća novi vektor koji je rotirani trenutni vektor.
	 * 
	 * @param angle
	 *            kut u stupnjevima
	 * @return rotirani vektor
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = this;
		newVector.rotate(angle);
		return newVector;
	}

	/**
	 * Metoda koja skalira trenutni vektor.
	 * 
	 * @param scaler
	 *            vrijednost skaliranja
	 */
	public void scale(double scaler) {
		double xNew = this.x * scaler;
		double yNew = this.y * scaler;
		this.x = xNew;
		this.y = yNew;

	}

	/**
	 * Metoda koja vraća novi vektor koji je skalirani trenutni vektor.
	 * 
	 * @param scaler
	 *            vrijednost skaliranja
	 * @return skalirani vektor
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = this;
		newVector.scale(scaler);
		return newVector;
	}

	/**
	 * Metoda vraća vektor identičan trenutnom.
	 * 
	 * @return kopiju vektora
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	/**
	 * Metoda vraća normalizirani trenutni vektor.
	 * 
	 * @return normalizirani vektor
	 */
	public Vector2D normalize() {
		double length = this.length(this.x, this.y);
		double xNew = this.x / length;
		double yNew = this.y / length;
		return new Vector2D(xNew, yNew);
	}

	/**
	 * Metoda računa duljinu vektora.
	 * 
	 * @param x
	 *            realni dio
	 * @param y
	 *            imaginarni dio
	 * @return duljina
	 */
	public double length(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		if (y > 0) {
			return String.format("%f + %fi", x, y);
		} else if (Math.abs(y) < THRESHOLD) {
			return String.format("%f", x);
		} else {
			return String.format("%f - %fi", x, Math.abs(y));
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Math.abs(this.x - other.x) > THRESHOLD)
			return false;
		if ((Math.abs(this.y - other.y) > THRESHOLD))
			return false;
		return true;
	}

}
