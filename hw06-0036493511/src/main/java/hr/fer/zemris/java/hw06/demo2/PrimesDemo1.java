package hr.fer.zemris.java.hw06.demo2;

/**
 * Razred predstavlja testni primjer iz domaće zadaće.
 * 
 * @author Filip
 *
 */
public class PrimesDemo1 {

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them

		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
