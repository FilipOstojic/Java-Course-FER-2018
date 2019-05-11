package hr.fer.zemris.java.hw05.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Primjer zadan u domaćoj zadaći. Ne treba unositi argumente.
 * 
 * @author Filip
 *
 */
public class Primjer4 {
	/**
	 * Metoda koja se zove kadase pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Filip", 5);
		examMarks.put("Marko", 5);

		System.out.printf("Veličina: %d%n%n", examMarks.size());

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.printf("Uklanjam: %s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}

		System.out.printf("%nVeličina: %d%n", examMarks.size());
	}
}
