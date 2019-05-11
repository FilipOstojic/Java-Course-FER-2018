package hr.fer.zemris.java.hw16.trazilica;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Razred predstavlja N-dimenzionalni vektor. Sadrži metode za normalizaciju,
 * skalarno množenje i najvažnija računanje kosinusa kuta između dva vektora.
 * 
 * @author Filip
 *
 */
public class Vector {
	/**
	 * Polje vrijednosti vektora.
	 */
	private double[] values;

	/**
	 * Konstruktor.
	 * 
	 * @param values
	 *            polje vrijednosti vektora
	 */
	public Vector(double... values) {
		this.values = values;
	}

	/**
	 * Metoda vraća duljinu vektora.
	 * 
	 * @return duljina vektora
	 */
	public double norm() {
		return Math.sqrt(this.dot(this));
	}

	/**
	 * Metoda skalarno množi dva vektora.
	 * 
	 * @param other
	 *            vektor
	 * @return vektor skalarnog umnoška
	 */
	public double dot(Vector other) {
		Objects.requireNonNull(other);
		if (this.values.length != other.values.length) {
			throw new RuntimeException("Vectors are not same sizes.");
		}
		double result = 0;

		for (int i = 0; i < values.length; i++) {
			result += this.values[i] * other.values[i];
		}

		return result;
	}

	/**
	 * Metoda vraća kosinus kuta između dva vektora.
	 * 
	 * @param other
	 *            vektor
	 * @return kosinus kuta
	 */
	public double cosAngle(Vector other) {
		Objects.requireNonNull(other);
		if (this.norm() == 0 || other.norm() == 0)
			return 0;
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Getter za vrijednosti vektora.
	 * 
	 * @return values polje vrijednosti
	 */
	public double[] getValues() {
		return values;
	}

	/**
	 * Setter za vrijednosti vektora.
	 * 
	 * @param values
	 *            polje vrijednosti
	 */
	public void setValues(double[] values) {
		this.values = values;
	}

	/**
	 * Ispis vektora u obliku: [x1,x2,x3..]
	 * 
	 * @return string reprezentacija vr+ektora
	 */
	public String getStringVector() {
		StringJoiner sj = new StringJoiner(",", "[", "]");
		for (Double value : values) {
			if (value == 0.)
				continue;
			sj.add(String.valueOf(value));
		}
		return sj.toString();
	}

}
