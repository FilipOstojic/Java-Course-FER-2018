package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Razred <code>Complex</code> predstavlja kompleksni broj te definira osnovne
 * računske operacije nad njima.
 * 
 * @author Filip
 *
 */
public class Complex {
	/**
	 * Realni dio kompleksnog broja.
	 */
	private double real;
	/**
	 * Imaginarni dio kompleksnog broja.
	 */
	private double imaginary;

	/**
	 * Complex number (0,0).
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Complex number (1,0).
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Complex number (-1,0).
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Complex number (0,1).
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Complex number (0,-1).
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Konstruktor, ne prima argumente.
	 */
	public Complex() {
		this(ZERO.real, ZERO.imaginary);
	}

	/**
	 * Konstruktor, prima realni i imaginarni dio kompleksnog broja.
	 * 
	 * @param real
	 *            realni dio
	 * @param imaginary
	 *            imaginarni dio
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Metoda izračunava udaljenost kompleksnog broja od središta koordinatnog
	 * sustava (modul).
	 * 
	 * @return udaljenost
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Metoda množi dva kompleksna broja.
	 * 
	 * @param other
	 *            kompleksni broj
	 * @return umnožak
	 */
	public Complex multiply(Complex other) {
		return new Complex(this.real * other.real - this.imaginary * other.imaginary,
				this.real * other.imaginary + this.imaginary * other.real);
	}

	/**
	 * Metoda dijeli dva kompleksna broja.
	 * 
	 * @param other
	 *            kompleksni broj
	 * @return količnik
	 */
	public Complex div(Complex other) {
		if (other == ZERO) {
			throw new IllegalArgumentException("Dividing with zero is not allowed.");
		}
		double denominator = other.real * other.real + other.imaginary * other.imaginary;
		double newReal = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
		double newImaginary = (this.imaginary * other.real - this.real * other.imaginary) / denominator;

		return new Complex(newReal, newImaginary);
	}

	/**
	 * Meotda zbraja dva kompleksna broja.
	 * 
	 * @param other
	 *            kompleksni broj
	 * @return zbroj
	 */
	public Complex add(Complex other) {
		return new Complex(this.real + other.real, this.imaginary + other.imaginary);
	}

	/**
	 * Metoda oduzima dva kompleksna broja.
	 * 
	 * @param other
	 *            kompleksni broj
	 * @return razlika
	 */
	public Complex sub(Complex other) {
		return new Complex(this.real - other.real, this.imaginary - other.imaginary);
	}

	/**
	 * Metoda vraća negirani kompleksni broj.
	 * 
	 * @return negirani kompleksni broj
	 */
	public Complex negate() {
		return ZERO.sub(this);
	}

	/**
	 * Metoda potencira kompleksni broj.
	 * 
	 * @param n
	 *            broj potencije (nenegativan cijeli broj)
	 * @return potencirani cijeli broj
	 * @throws IllegalArgumentException-
	 *             ako je broj potencije nenegativan broj
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("The required power must be positive or zero.");
		switch (n) {
		case 0:
			return Complex.ONE;
		case 1:
			return this;
		}

		Complex result = new Complex(real, imaginary);
		for (int i = 1; i < n; i++) {
			result = result.multiply(this);
		}
		return result;
	}

	/**
	 * Metoda izračunava korijene kompleksnog broja korištenjem De Moivreovog
	 * teorema.
	 * 
	 * @param n
	 *            broj željenog korijena (pozitivni cijeli broj)
	 * @return listu kopleksnih brojeva koji predstavljaju korijene
	 * @throws IllegalArgumentException-
	 *             ako je broj korijena negativan ili nula
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("The required root must be positive.");
		}
		List<Complex> roots = new ArrayList<>();

		double newMagnitude = pow(module(), (double) 1.0 / n);
		double newReal, newImaginary;

		for (int i = 0; i < n; i++) {
			newReal = newMagnitude * cos((double) (getAngle() + 2 * i * PI) / n);
			newImaginary = newMagnitude * sin((double) (getAngle() + 2 * i * PI) / n);
			roots.add(new Complex(newReal, newImaginary));
		}
		return roots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see String#toString()
	 */
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "[%f, %fi]", real, imaginary);
	}

	/**
	 * Izračunava kut apscise i polupravca iz ishodišta kroz kompleksan broj. Kut je
	 * u radijanima.
	 * 
	 * @return kut u radijanima
	 */
	private double getAngle() {
		return atan2(imaginary, real);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Object#equals()
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Math.abs(this.real - other.real) > 0.001)
			return false;
		if (Math.abs(this.imaginary - other.imaginary) > 0.001)
			return false;
		return true;
	}

	/**
	 * Metoda vraća realni dio kompleksnog broja.
	 * 
	 * @return realni dio kompleksnog broja
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Metoda vraća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio kompleksnog broja
	 */
	public double getImaginary() {
		return imaginary;
	}
}
