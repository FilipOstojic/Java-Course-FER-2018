package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Razred <code>StudentDemo</code> ispisuje na standardni izlaz razne rezultate
 * obrada kolekcije studenata. Ne očekuju se argumenti.
 * 
 * @author Filip
 *
 */
public class StudentDemo {

	/**
	 * Metoda se poziva kada s pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StudentRecord> records = convert(lines);

		System.out.println("1. Broj studenata koji imaju više bodova od 25: " + vratiBodovaViseOd25(records) + "\n");

		System.out.println("2. Broj odlikaša: " + vratiBrojOdlikasa(records) + "\n");

		System.out.println("3. Rekordi odlikaša: ");
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		odlikasi.stream().forEach(System.out::println);

		System.out.println("\n4. Rekordi odlikaša sortirano od najboljeg: ");
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		odlikasiSortirano.stream().forEach(System.out::println);

		System.out.println("\n5. JMBAG-ovi onih koji nisu položili: ");
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("(size) " + nepolozeniJMBAGovi.stream().count());

		System.out.println("\n6. Mapa po ocijenama: ");
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		for (int i = 1; i <= 5; i++) {
			System.out.format("%d -> %d%n", i, mapaPoOcjenama.get(i).size());
		}

		System.out.println("\n7. Mapa po ocijenama (trebala bi biti ista kao prošla): ");
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		for (int i = 1; i <= 5; i++) {
			System.out.format("%d -> %d%n", i, mapaPoOcjenama2.get(i));
		}

		System.out.println("\n8. Mapa prolaz i pad: ");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("pad (size): " + prolazNeprolaz.get(false).size());
		System.out.println("prolaz (size): " + prolazNeprolaz.get(true).size());
	}

	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		Map<Boolean, List<StudentRecord>> razvrstajProlazPad = records.stream()
				.collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
		return razvrstajProlazPad;
	}

	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		Map<Integer, Integer> mapaPoOcjenama = records.stream()
				.collect(Collectors.toMap(StudentRecord::getFinalGrade, s -> 1, Integer::sum));
		return mapaPoOcjenama;
	}

	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
		return mapaPoOcjenama;
	}

	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		List<String> nepolozeniJMBAGovi = records.stream().filter(s -> s.getFinalGrade() == 1)
				.sorted(StudentRecord.BY_JMBAG).map(s -> s.getJmbag()).collect(Collectors.toList());
		return nepolozeniJMBAGovi;
	}

	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasi = records.stream().filter(s -> s.getFinalGrade() == 5)
				.sorted(StudentRecord.BY_TOTAL_POINTS).collect(Collectors.toList());
		return odlikasi;
	}

	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasi = records.stream().filter(s -> s.getFinalGrade() == 5)
				.collect(Collectors.toList());
		return odlikasi;
	}

	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long broj = records.stream().filter(s -> s.getFinalGrade() == 5).count();
		return broj;
	}

	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long broj = records.stream().filter(s -> s.getTotalPoints() > 25).count();
		return broj;
	}

	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();

		for (String line : lines) {
			if (line.equals("") || line.isEmpty())
				continue;
			String[] chunks = line.split("\\t+");
			records.add(new StudentRecord(chunks[0], chunks[2], chunks[1], Double.parseDouble(chunks[3]),
					Double.parseDouble(chunks[4]), Double.parseDouble(chunks[5]), Integer.parseInt(chunks[6])));
		}
		return records;
	}
}
