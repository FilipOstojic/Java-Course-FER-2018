package hr.fer.zemris.java.hw06.demo2;

/**
 * Razred predstavlja testni primjer iz domaće zadaće.
 * 
 * @author Filip
 *
 */
public class PrimesDemo2 {

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);

		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
