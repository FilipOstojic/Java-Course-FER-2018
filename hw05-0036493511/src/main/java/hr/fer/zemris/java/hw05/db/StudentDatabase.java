package hr.fer.zemris.java.hw05.db;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;
import hr.fer.zemris.java.hw05.collections.SimpleHashtable.TableEntry;

/**
 * Razred <code>StudentDatabase</code> predstavlja bazu podataka o studentima.
 * 
 * @author Filip
 *
 */
public class StudentDatabase {

	/**
	 * Tablica raspršenog adresiranja u kojoj su pohranjeni podatci o studentima.
	 */
	private SimpleHashtable<String, StudentRecord> records = new SimpleHashtable<>();
	/**
	 * Lista u kojoj se čuvaju zapisi o studentima, jedan redak je jedan element
	 * liste.
	 */
	private List<StudentRecord> list = new LinkedList<>();

	/**
	 * Konstruktor, prima listu stringova.
	 * 
	 * @param lines
	 *            lista stringova (podatci o studentima)
	 */
	public StudentDatabase(List<String> lines) {
		for (String line : lines) {
			list.add(makeRecord(line));
			records.put(line.substring(0, 10), makeRecord(line));
		}
	}

	/**
	 * Pomoćna metoda koja iz stringa stvara zapis o studentu.
	 * 
	 * @param line
	 *            ulazna linija (padatci o studentu)
	 * @return {@link StudentRecord}
	 */
	private StudentRecord makeRecord(String line) {
		String[] array = line.split("\t+");
		String jmbag, firstName, lastName, finalGrade;

		if (array.length == 4) {
			jmbag = array[0];
			lastName = array[1];
			firstName = array[2];
			finalGrade = array[3];
		} else {
			throw new IllegalArgumentException("Line should have 5 or 6 elements.");
		}
		return new StudentRecord(jmbag, firstName, lastName, finalGrade);
	}

	/**
	 * Metoda vraća zapis o studentu čiji je jmbag primila.
	 * 
	 * @param jmbag
	 *            jmbag studenta
	 * @return {@link StudentRecord}
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (!records.containsKey(jmbag))
			return null;
		return records.get(jmbag);
	}

	/**
	 * Metoda filtrira trenutnu listu studenata te vraća novu listu, samo onih
	 * studenata koji su prošli filtar.
	 * 
	 * @param filter
	 *            {@link IFilter}
	 * @return filtrirana lista studenata
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new LinkedList<>();

		Iterator<TableEntry<String, StudentRecord>> iter = records.iterator();
		while (iter.hasNext()) {
			TableEntry<String, StudentRecord> pair = iter.next();
			if (filter.accepts(pair.getValue()))
				filteredList.add(pair.getValue());
		}
		return filteredList;
	}

}
