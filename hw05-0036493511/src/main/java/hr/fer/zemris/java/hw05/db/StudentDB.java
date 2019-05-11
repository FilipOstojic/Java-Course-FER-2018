package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Razred koji preko standardnog ulaza dobiva query-je od korisnika te ispisuje
 * listu studenata koji zadovoljavaju query. Ne prima argumente.
 * 
 * @author Filip
 *
 */
public class StudentDB {
	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		/**
		 * Scanner za interakciju s korisnikom.
		 */
		Scanner sc = new Scanner(System.in);
		/**
		 * Filter.
		 */
		QueryFilter queryFilter;
		/**
		 * Parser.
		 */
		QueryParser parser;
		/**
		 * Lista stringova o studentima.
		 */
		List<String> lines = null;
		/**
		 * Filtrirana lista zapisa o studentima koji zadovoljavaju query.
		 */
		List<StudentRecord> filteredRecords;

		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
		}

		/**
		 * Baza zapisa o studentima.
		 */
		StudentDatabase db = new StudentDatabase(lines);
		System.out.print("> ");

		while (true) {
			String line = sc.nextLine().trim();

			if (line.startsWith("query")) {
				line = line.replaceFirst("query", "");
				parser = new QueryParser(line);
				queryFilter = new QueryFilter(parser.getQuery());
				filteredRecords = db.filter(queryFilter);
				print(filteredRecords, parser.isDirectQuery());
			} else if (line.startsWith("exit")) {
				System.out.println("Goodbye!");
				break;
			} else {
				sc.close();
				throw new IllegalArgumentException("Input should start with commands query or exit, was: \n" + line);
			}
		}
		sc.close();
	}

	/**
	 * Metoda za ispis liste studenata koji zadovoljavaju query.
	 * 
	 * @param filteredRecords
	 *            lista studenata koji zadovoljavaju query
	 * @param isDirectQuery
	 *            zastavica, true ako je direktan query, inače false
	 */
	private static void print(List<StudentRecord> filteredRecords, boolean isDirectQuery) {
		if (filteredRecords.size() != 0) {
			int nameLen = getMaxFirstNameLength(filteredRecords);
			int surnameLen = getMaxLastNameLength(filteredRecords);
			if (isDirectQuery) {
				System.out.println("Using index for record retrieval.");
			}
			printLine(surnameLen, nameLen);
			for (StudentRecord record : filteredRecords) {
				System.out.format("| %10s | %s | %s | %s |\n", record.getJmbag(),
						getSpacedName(record.getLastName(), surnameLen), getSpacedName(record.getFirstName(), nameLen),
						record.getFinalGrade());
			}
			printLine(surnameLen, nameLen);
		}
		System.out.println("Records selected: " + filteredRecords.size());
		System.out.print("> ");
	}

	/**
	 * Pomoćna metoda koja prima ime/prezime i maksimalnu duljinu. Popunjava
	 * ime/prezime s prazninama da poravna ispis.
	 * 
	 * @param name
	 *            ime ili prezime studenta
	 * @param size
	 *            maksimalna duljina imena ili prezimena
	 * @return ime/prezime s razmacima
	 */
	private static Object getSpacedName(String name, int size) {
		String result = name;
		for (int i = name.length(); i < size; i++) {
			result += " ";
		}
		return result;
	}

	/**
	 * Metoda ispisuje početnu i završnu liniju u ovisnosti o maksimalnim duljinama
	 * imena i prezimena.
	 * 
	 * @param surnameLen
	 *            maksimalna duljina prezimena
	 * @param nameLen
	 *            maksimalna duljina imena
	 */
	private static void printLine(int surnameLen, int nameLen) {
		System.out.print("+============+");
		System.out.print(getEquals(surnameLen) + "+");
		System.out.print(getEquals(nameLen) + "+");
		System.out.println("===+");
	}

	/**
	 * Pomoćna metoda vraća znakova '=' onliko koliko mu je zadnao.
	 * 
	 * @param length
	 *            duljina znakova
	 * @return length puta znak '='
	 */
	private static String getEquals(int length) {
		String result = "==";
		for (int i = 0; i < length; i++) {
			result += "=";
		}
		return result;
	}

	/**
	 * Metoda računa maksimalnu duljinu prezimena u filtriranoj listi studenata.
	 * 
	 * @param filteredRecords
	 *            filtrirana lista studenata
	 * @return maksimalna duljina
	 */
	private static int getMaxLastNameLength(List<StudentRecord> filteredRecords) {
		int maxLength = 0;
		for (StudentRecord record : filteredRecords) {
			if (record.getLastName().length() > maxLength) {
				maxLength = record.getLastName().length();
			}
		}
		return maxLength;
	}

	/**
	 * Metoda računa maksimalnu duljinu imena u filtriranoj listi studenata.
	 * 
	 * @param filteredRecords
	 *            filtrirana lista studenata
	 * @return maksimalna duljina
	 */
	private static int getMaxFirstNameLength(List<StudentRecord> filteredRecords) {
		int maxLength = 0;
		for (StudentRecord record : filteredRecords) {
			if (record.getFirstName().length() > maxLength) {
				maxLength = record.getFirstName().length();
			}
		}
		return maxLength;
	}
}
