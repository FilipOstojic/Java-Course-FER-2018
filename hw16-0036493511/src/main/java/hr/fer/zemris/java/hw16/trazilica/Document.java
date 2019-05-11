package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static hr.fer.zemris.java.hw16.util.Util.*;

/**
 * Razred predstavlja dokument. Sadrži njegovu putanju, vektor, sličnost s
 * drugim dokumentom.
 * 
 * @author Filip
 *
 */
public class Document {

	/**
	 * Putanja do dokumenta.
	 */
	private Path path;
	/**
	 * Sličnost s drugim dokumentom.
	 */
	private Double similarity;
	/**
	 * Vektor.
	 */
	private Vector vector;
	/**
	 * Mapa učestalosti pojavljivanja riječi u dokumentu.
	 */
	private Map<String, Integer> frequency = new HashMap<>();
	/**
	 * Konstanta, broj 1.
	 */
	private final static int ONE = 1;
	/**
	 * Broj dokumenata.
	 */
	private final static double NO_OF_DOCUMENTS = 60;

	/**
	 * Konstruktor.
	 * 
	 * @param path
	 *            putanja do dokumenta
	 */
	public Document(Path path) {
		this.path = path;
	}

	/**
	 * Getter za putanju.
	 * 
	 * @return putanja dokumenta
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Getter za sličnost.
	 * 
	 * @return sličnost
	 */
	public Double getSimilarity() {
		return similarity;
	}

	/**
	 * Getter za vektor.
	 * 
	 * @return vektor
	 */
	public Vector getVector() {
		return vector;
	}

	/**
	 * Getter za mapu učestalosti.
	 * 
	 * @return {@linkplain #frequency}
	 */
	public Map<String, Integer> getFrequencyMap() {
		return frequency;
	}

	/**
	 * Povećava vrijednost riječi u mapi {@linkplain #frequency} za 1.
	 * 
	 * @param word
	 *            riječ
	 */
	public void addWord(String word) {
		frequency.merge(word, ONE, (oldValue, value) -> oldValue + value);
	}

	/**
	 * Getter za broj ponavljanja riječi u dokumentu.
	 * 
	 * @param word
	 *            riječ
	 * @return broj ponavljanja
	 */
	public int getFrequency(String word) {
		return frequency.get(word) == null ? 0 : frequency.get(word);
	}

	/**
	 * Računa sličnost ovog dokumenta i argumenta.
	 * 
	 * @param other
	 *            {@link Document}
	 */
	public void calculateSimilarity(Document other) {
		this.similarity = this.vector.cosAngle(other.vector);
	}

	/**
	 * Metoda računa vektor dokumenta.
	 */
	public void calculateVector() {
		double[] values = new double[vocabulary.size()];
		for (int i = 0; i < vocabulary.size(); i++) {
			String word = vocabulary.get(i);
			int frequencyInDocument = this.getFrequency(word);
			double log = Math.log10(NO_OF_DOCUMENTS / countDistinctWordInAllDocuments(word));
			double value = frequencyInDocument * log;
			values[i] = Double.isNaN(value) ? 0.0 : value;
		}
		vector = new Vector(values);
	}
}
