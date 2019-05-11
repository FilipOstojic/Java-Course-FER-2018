package hr.fer.zemris.math;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Razred <code>ComplexPolynomial</code> predstavlja polinom oblika: <BR>
 * f(z) = zn*z^n + ... + z1*z + z0 , gdje su z1 do zn faktori.
 * 
 * @author Filip
 *
 */
public class ComplexPolynomial {
	/**
	 * Polje faktora.
	 */
	private Complex[] factors;

	/**
	 * Konstruktor, prima polje faktora.
	 * 
	 * @param factors
	 *            polje faktora
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors);
		if (factors.length == 0) {
			throw new IllegalArgumentException("Expecting at least one factor, recived 0.");
		}
		this.factors = factors;
	}

	/**
	 * Metoda vraća najveću potenciju polinoma.
	 * 
	 * @return najveća potencija polinoma
	 */
	public short order() {
		return (short) ((short) factors.length - 1);
	}

	/**
	 * Metoda množi dva polinoma oblika {@linkplain ComplexPolynomial}.
	 * 
	 * @param other
	 *            {@linkplain ComplexPolynomial}
	 * @return umnožak polinoma
	 */
	public ComplexPolynomial multiply(ComplexPolynomial other) {
		Complex[] newFactors = new Complex[this.order() + other.order() + 1];

		for (int i = 0; i <= this.order(); i++) {
			for (int j = 0; j <= other.order(); j++) {
				newFactors[i + j] = newFactors[i + j] == null ? Complex.ZERO : newFactors[i + j];
				newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(other.factors[j]));
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Metoda računa derivaciju polinoma {@linkplain ComplexPolynomial}.
	 * 
	 * @return derivirani polinom
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[order()];
		for (int i = 1; i <= order(); i++) {
			newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Metoda računa vrijednost polinoma u točki z.
	 * 
	 * @param z
	 *            kompleksni broj
	 * @return vrijednost poliinom au točki z
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();
		for (int i = 0; i <= order(); i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		return result;
	}

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner("+");
		for (int i = order() ; i >= 0; i--) {
			sj.add(factors[i].toString()+ "*" + "z^" + i);
		}
		return sj.toString();
	}

	/**
	 * Metoda vraća polje faktora.
	 * 
	 * @return polje faktora.
	 */
	public Complex[] getFactors() {
		return factors;
	}

}
