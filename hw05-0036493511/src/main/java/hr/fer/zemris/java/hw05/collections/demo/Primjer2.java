package hr.fer.zemris.java.hw05.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Primjer zadan u domaćoj zadaći. Ne treba unositi argumente.
 * 
 * @author Filip
 *
 */
public class Primjer2 {
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

		System.out.println(examMarks);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}

		System.out.println(examMarks);
	}
}
