package hr.fer.zemris.math;

import java.util.Locale;
import java.util.Objects;

/**
 * Razred <code>Vector3</code> predstavlja razred koji opisuje konstantan vektor
 * u Euklidskom prostoru te osnovne računske operacije nad vektorima.
 * 
 * @author Filip
 *
 */
public class Vector3 {
	/**
	 * Vrijednost apscise.
	 */
	private final double x;
	/**
	 * Vrijednost ordinate.
	 */
	private final double y;
	/**
	 * Vrijednost aplikante.
	 */
	private final double z;
	/**
	 * Prag tolerancije za realne brojeb+ve.
	 */
	private static double treshold = 10E-4;

	/**
	 * Konstruktor, prima komponente vektora po osima.
	 * 
	 * @param x
	 *            apscisa
	 * @param y
	 *            ordinata
	 * @param z
	 *            aplikanta
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Metoda vraća duljinu vektora.
	 * 
	 * @return duljina vektora
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Metoda vraća normalizirani vektor.
	 * 
	 * @return normalizitani vektor
	 */
	public Vector3 normalized() {
		return norm() == 0 ? new Vector3(0, 0, 0) : new Vector3(x / norm(), y / norm(), z / norm());
	}

	/**
	 * Metoda zbraja dva vektora.
	 * 
	 * @param other
	 *            vektor
	 * @return vektor zbroja
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * Metoda oduzima dva vektora.
	 * 
	 * @param other
	 *            vektor
	 * @return vektor razlike
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);
		return this.add(other.scale(-1.0));
	}

	/**
	 * Metoda skalarno množi dva vektora.
	 * 
	 * @param other
	 *            vektor
	 * @return vektor skalarnog umnoška
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	/**
	 * Metoda vektorski množi dva vektora.
	 * 
	 * @param other
	 *            vektor
	 * @return rezultat vektorskog umnoška
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);
		double newX = this.y * other.z - other.y * this.z;
		double newy = -this.x * other.z + other.x * this.z;
		double newZ = this.x * other.y - other.x * this.y;
		return new Vector3(newX, newy, newZ);
	}

	/**
	 * Metoda skalira vektor.
	 * 
	 * @param s
	 *            faktor skaliranja
	 * @return skalirani vektor
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	/**
	 * Metoda vraća kosinus kuta između dva vektora.
	 * 
	 * @param other
	 *            vektor
	 * @return kosinus kuta
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other);
		if (this.norm() == 0 || other.norm() == 0)
			return 0;
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Metoda dohvaća vrijednost x.
	 * 
	 * @return vrijednost x komponente vektora
	 */
	public double getX() {
		return x;
	}

	/**
	 * Metoda dohvaća vrijednost y.
	 * 
	 * @return vrijednost y komponente vektora
	 */
	public double getY() {
		return y;
	}

	/**
	 * Metoda dohvaća vrijednost z.
	 * 
	 * @return vrijednost z komponente vektora
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Metoda vraća polje vrijednosti komponenti vektora
	 * 
	 * @return polje komponenti vektora [x, y, z]
	 */
	public double[] toArray() {
		return new double[] { getX(), getY(), getZ() };
	}

	/**
	 * Metoda vraća string reprezentaciju vektora.
	 */
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "(%f, %f, %f)", getX(), getY(), getZ());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (Math.abs(this.x - other.x) > treshold)
			return false;
		if (Math.abs(this.y - other.y) > treshold)
			return false;
		if (Math.abs(this.z - other.z) > treshold)
			return false;
		return true;
	}

}
