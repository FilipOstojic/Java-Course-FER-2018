package hr.fer.zemris.java.hw16.trazilica;

import static hr.fer.zemris.java.hw16.util.Util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Glavni razred, predstavlja konzolu za unos različitih upita u pretraživanju
 * tekstualnih datoteka. Rezultat pretrage je 10 dokumenata (članaka) koji su
 * najsličniji unesenom upitu poredani po sličnosti. Očekuje se jedan argument:
 * putanja do direktorija s kolekcijom dokumenata (članaka).
 * 
 * @author Filip
 *
 */
public class Konzola {

	/**
	 * Metoda se pokreće kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komendne linije
	 */
	public static void main(String[] args) {
		if (!validArgument(args)) {
			System.out.println("Expected path to directory with articles");
			return;
		}
		try {
			initStopWords();
			initVocabulary(Paths.get(args[0]));
			initDocuments();

			System.out.println("Preparing console. Please wait...");
			System.out.println(String.format("Vocabulary size: %d", vocabulary.size()));
			System.out.println("Welcome!");
			Scanner sc = new Scanner(System.in);
			do {
				System.out.print("\nEnter command> ");
				String command = sc.nextLine().trim();
				String comandName = command.split("\\s+")[0];

				switch (comandName) {
				case "query":
					processQuery(command);
					break;
				case "type":
					processType(command);
					break;
				case "results":
					processResults(command);
					break;
				case "exit":
					System.out.println("\nGoodbye!\n");
					sc.close();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid command, please try again.");
					break;
				}
				
			} while (true);
		} catch (IOException e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

	/**
	 * Naredba koja ispisuje riječi iz upita koje postoje u vokabularu. Računa
	 * sličnosti dokumenata i ispisuje 10 najsličnijih dokumenata (kao
	 * {@linkplain #processResults(String)}).
	 * 
	 * @param command
	 *            naredba koja započinje s "query "
	 */
	private static void processQuery(String command) {
		String[] words = command.split("\\s+");

		if (words.length == 1) {
			System.out.println("Incorrect command.");
			return;
		}

		StringJoiner sj = new StringJoiner(",", "Query is: [", "]");

		for (int i = 1; i < words.length; i++) {
			if (vocabulary.contains(words[i].trim())) {
				sj.add(words[i].trim());
			}
		}
		System.out.println(sj.toString());
		calculateSimilarity(command);
		System.out.println("Top 10 results: ");
		processResults(command);
	}

	/**
	 * Metoda prima naredbu koja započinje s "type ". Ispravna naredba bi bila koja
	 * iza toga ima cijeli broj N iz intervala [0-9]. Metoda ispisuje sadržaj
	 * dokumenta koji je N-ti po sličnosti.
	 * 
	 * @param command
	 *            naredba koja započinje s "type "
	 */
	private static void processType(String command) {
		String[] chunks = command.split("\\s+");
		if (chunks.length != 2) {
			System.out.println("Incorrect command.");
			return;
		}
		try {
			int number = Integer.parseInt(chunks[1]);
			if (number >= results.size() || number > 9) {
				System.out.println("Index out of bunds.");
				return;
			}
			printText(results.get(number));
			
		} catch (NumberFormatException e) {
			System.out.println("Expected Integer, was: " + chunks[1]);
		} catch (IOException ex) {
			System.out.println("Results are not initialised.");
		}
	}

	/**
	 * Metoda ispisuje 10 najboljih rezultata (10 najsličnijih dokumenata) poredanih
	 * od najsličnijeg. Ispis je u obliku: <br>
	 * [redni broj] (sličnost) "putanja do datoteke" <br>
	 * Ispis može sadržavati i manje od 10 rezultata ako im je sličnost jednaka 0.
	 * 
	 * @param command
	 *            naredba koja započinje s "results "
	 */
	private static void processResults(String command) {
		if (results == null || results.size() == 0) {
			System.out.println("Results are not initialised.");
			return;
		}

		for (int i = 0; i < results.size() && i < 10; i++) {
			Document doc = results.get(i);
			System.out.println(String.format("[%2d] (%.4f) %s", i, doc.getSimilarity(), doc.getPath()));
		}
	}

	/**
	 * Pomoćna metoda, provjerava ispravnost unesenih argumenata.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 * @return true ako je argument ispravan, inače false
	 */
	private static boolean validArgument(String[] args) {
		if (args.length != 1)
			return false;
		Path path = Paths.get(args[0]);
		if (!Files.exists(path) || !Files.isDirectory(path))
			return false;
		return true;
	}
}
