package hr.fer.zemris.math;

import java.util.StringJoiner;

/**
 * Razred <code>ComplexRootedPolynomial</code> predstavlja polinom oblika: <BR>
 * f(z) = (z-z1)*(z-z2)*...*(z-zn) , gdje su z1 do zn njegove nultočke.
 * 
 * @author Filip
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * Polje nultočki polinoma.
	 */
	private Complex[] roots;

	/**
	 * Konstruktor, prima polje kompleksnih brojeva (nultočke).
	 * 
	 * @param roots
	 *            polje nultočki polinoma
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Metoda izračunava vrijednost polinoma u točki z.
	 * 
	 * @param z
	 *            kompleksni broj
	 * @return vrijednost polinom au točki z
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for (Complex c : roots) {
			result = result.multiply((z.sub(c)));
		}
		return result;
	}

	/**
	 * Metoda pretvara polinom oblika f(z) = (z-z1)*(z-z2)*...*(z-zn) , gdje su z1
	 * do zn njegove nultočke u polinom oblika f(z) = zn*z^n + ... + z1*z + z0 .
	 * 
	 * @return {@link ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);
		for (Complex c : roots) {
			result = result.multiply(new ComplexPolynomial(c.negate(), Complex.ONE));
		}
		return result;
	}

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner("*");
		for (int i = 0; i < roots.length; i++) {
			sj.add("(z - " + roots[i].toString() + ")");
		}
		return sj.toString();
	}

	/**
	 * Metoda vraća indeks broja iz polja nultočki onog broja koji je najbliži
	 * unesenom kompleksnom broju te ako zadovoljava interval udaljenosti od zadanog
	 * broja.
	 * 
	 * @param z
	 *            kompleksni broj
	 * @param treshold
	 *            interval tolerirajuće vrijednosti udaljenosti od kompleksnog broja
	 * @return indeks broja
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int result = -1;
		double delta = 0;
		for (int i = 0; i < roots.length; i++) {
			if (z.sub(roots[i]).module() < treshold) {
				if (result == -1) {
					delta = z.sub(roots[i]).module();
					result = i;
				} else {
					if (z.sub(roots[i]).module() < delta) {
						delta = z.sub(roots[i]).module();
						result = i;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Metoda vraća polje nultočki polinoma.
	 * 
	 * @return polje nultočki
	 */
	public Complex[] getRoots() {
		return roots;
	}

}
