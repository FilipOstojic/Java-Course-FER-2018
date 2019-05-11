package hr.fer.zemris.java.hw05.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Primjer zadan u domaćoj zadaći. Ne treba unositi argumente.
 * 
 * @author Filip
 *
 */
public class Primjer3 {
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

		try {
			Iterator<SimpleHashtable.TableEntry<String, Integer>> iter2 = examMarks.iterator();
			while (iter2.hasNext()) {
				SimpleHashtable.TableEntry<String, Integer> pair = iter2.next();
				if (pair.getKey().equals("Ivana")) {
					iter2.remove();
					iter2.remove();
				}
			}
		} catch (IllegalStateException e) {
			System.out.println("Očekivana greška uhvaćena!");
		}
	}
}
