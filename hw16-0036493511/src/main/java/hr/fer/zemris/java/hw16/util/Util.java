package hr.fer.zemris.java.hw16.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw16.trazilica.Document;

/**
 * Pomoćni razred koji sadrži metode za inicijalizaciju vokabulara te
 * inicijalizaciju "stop riječi" hrvatskog jezika.
 * 
 * @author Filip
 *
 */
public class Util {
	/**
	 * Putanja do datoteke u kojoj se nalaze "stop riječi" hrvatskog jezika.
	 */
	private static String PATH_TO_STOP_WORDS = "src/main/resources/hrvatski_stoprijeci.txt";
	/**
	 * Duljina riječi "Dokument :" .
	 */
	private static int INITIAL_LENGTH = 10;
	/**
	 * Skup "stop riječi" hrvatskog jezika.
	 */
	public static Set<String> stopWords;
	/**
	 * Skup riječi pronađenih u člancima.
	 */
	public static List<String> vocabulary;
	/**
	 * Lista svih dokumenata.
	 */
	public static List<Document> documents = new ArrayList<>();
	/**
	 * Lista najsličnijih dokumenata.
	 */
	public static List<Document> results = new ArrayList<>();

	/**
	 * Metoda gradi vokabular iz svih članaka iz dobiveneg direktorija.
	 * 
	 * @param dir
	 *            putanja do direktorija koji sadrži članke
	 * @throws IOException
	 *             u slučaju pogreške
	 */
	public static void initVocabulary(Path dir) throws IOException {
		vocabulary = new ArrayList<>();

		List<Path> paths = Files.list(dir).collect(Collectors.toList());
		for (Path path : paths) {
			addWordsInVocabularyFrom(path);
		}
	}

	/**
	 * Metoda dodaje nove riječi iz datoteke u listu {@linkn #vocabulary}.
	 * 
	 * @param path
	 *            putanja do datoteke
	 * @throws IOException
	 *             u slučaju pogreške
	 */
	private static void addWordsInVocabularyFrom(Path path) throws IOException {
		StringBuilder sb = new StringBuilder();
		Document document = new Document(path);
		for (String line : Files.readAllLines(path, Charset.forName("UTF-8"))) {
			for (Character c : line.toCharArray()) {
				if (Character.isAlphabetic(c) || c=='-') {
					sb.append(c);
					continue;
				} 
				String word = sb.toString().toLowerCase();
				if (!stopWords.contains(word)) {
					document.addWord(word);
					if (!vocabulary.contains(word)) {
						vocabulary.add(word);
					}
				}
				sb = new StringBuilder();
			}
			sb = new StringBuilder();
		}
		documents.add(document);
	}
	
	/**
	 * Metoda za svaki dokment u listi dokumenata računa njegov vektor.
	 * 
	 * @throws IOException
	 *             u slučaju pogreške
	 */
	public static void initDocuments() throws IOException {
		for (Document doc : documents) {
			doc.calculateVector();
		}
	}

	/**
	 * Metoda parsira dokument i puni listu {@link #stopWords} "stop riječima"
	 * hrvatskog jezika.
	 * 
	 * @throws IOException
	 *             u slučaju pogreške
	 */
	public static void initStopWords() throws IOException {
		stopWords = new HashSet<>();
		Path path = Paths.get(PATH_TO_STOP_WORDS);
		for (String word : Files.readAllLines(path, Charset.forName("UTF-8"))) {
			stopWords.add(word.toLowerCase());
		}
	}

	/**
	 * Metoda broji u koliko se dokumenta pojavaljuje zadana riječ.
	 * 
	 * @param word
	 *            zadana riječ
	 * @return broj dokumenta u kojima se pojavljuje zadana riječ
	 */
	public static int countDistinctWordInAllDocuments(String word) {
		int counter = 0;
		for (Document doc : documents) {
			if (doc.getFrequency(word) != 0)
				counter++;
		}
		return counter;
	}

	/**
	 * Metoda koja prolazi kroz dokumente i računa njihovu sličnost s dokumentom iz
	 * upita. Dokumente sortira po sličnosti.
	 * 
	 * @param command
	 *            naredba koja počinje s "query "
	 */
	public static void calculateSimilarity(String command) {
		Document inputDocument = new Document(null);
		String[] words = command.substring(5).trim().split("\\s+");
		for (String word : words) {
			inputDocument.addWord(word);
		}
		inputDocument.calculateVector();

		for (Document doc : documents) {
			doc.calculateSimilarity(inputDocument);
		}

		results = documents.stream()
				.filter(d -> d.getSimilarity() != 0)
				.sorted((d1, d2) -> d2.getSimilarity().compareTo(d1.getSimilarity()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Pomoćna metoda koja ispisuje putanju dokumenta te onda i sadržaj dokumenta.
	 * 
	 * @param document
	 *            {@link Document}
	 * @throws IOException
	 *             u slučaju pogreške
	 */
	public static void printText(Document document) throws IOException {
		String minus = "-";
		int pathLength = document.getPath().toString().length();
		String barrier = String.join("", Collections.nCopies(INITIAL_LENGTH + pathLength, minus));
		
		System.out.println(barrier);
		System.out.println("Dokument: " + document.getPath());
		System.out.println(barrier);
		
		List<String> lines = Files.readAllLines(document.getPath());

		for (String line : lines) {
			System.out.println(line);
		}
		System.out.println(barrier);
	}
}
