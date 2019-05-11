package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Razred <code>ObjectMultistack</code> predstavlja vrstu stoga koja sprema
 * primjerka razreda {@link MultistackEntry}. Sadrži inačice naredbi za rad sa
 * stogom kao što su push, pop, peek itd.
 * 
 * @author Filip
 *
 */
public class ObjectMultistack {
	/**
	 * Mapa: ključ je String, a vrijednost je {@link MultistackEntry}.
	 */
	Map<String, MultistackEntry> map = new HashMap<>();

	/**
	 * Metoda stavlja novi {@link MultistackEntry} u mapu. Ako ključ postoji
	 * nadovezuje ga u jednostruko povezanu listu na početak, ako ne postoji onda ga
	 * dodaje u mapu. Složenost je O(1).
	 * 
	 * @throws NullPointerException
	 *             ako je ključ null referenca
	 * @param name
	 *            ključ
	 * @param valueWrapper
	 *            vrijednost
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (!isEmpty(name)) {
			MultistackEntry newFirst = new MultistackEntry(valueWrapper, map.get(name));
			map.put(name, newFirst);
		} else {
			map.put(name, new MultistackEntry(valueWrapper, null));
		}
	}

	/**
	 * Metoda uklanja {@link MultistackEntry} sa zadanim ključem, ako ključ postoji.
	 * Uklanja onaj {@link MultistackEntry} koji se zadnji dodao s tim ključem
	 * (LIFO). Složenost je O(1).
	 * 
	 * @param name
	 *            ključ
	 * @return vrijednost
	 */
	public ValueWrapper pop(String name) {
		if (isEmpty(name))
			throw new EmptyStackException("Stack does not contain that key.");
		ValueWrapper returnValue = map.get(name).value;
		MultistackEntry nextEntry = map.get(name).next;
		if (nextEntry != null) {
			map.put(name, nextEntry);
		} else {
			map.remove(name);
		}
		return returnValue;
	}

	/**
	 * Metoda vraća zadnju vrijednost sa zadanim ključem, ako ključ postoji.
	 * 
	 * @param name
	 *            ključ
	 * @return vrijednost
	 */
	public ValueWrapper peek(String name) {
		if (isEmpty(name))
			throw new EmptyStackException("Stack does not contain that key.");
		return map.get(name).getValue();
	}

	/**
	 * Metoda provjerava postoji li zadani ključ u mapi.
	 * 
	 * @throws NullPointerException
	 *             ako je ključ null referenca
	 * @param name
	 *            ključ
	 * @return true ako sadrži ključ, inače false
	 */
	public boolean isEmpty(String name) {
		if (name == null)
			throw new NullPointerException("Key should not be null reference.");
		if (map.containsKey(name))
			return false;
		return true;
	}

	/**
	 * Razred <code>MultistackEntry</code> predstavlja čvor jednostruko povezane
	 * liste. Sadrži omotač i referencu na sljedeći <code>MultistackEntry</code>.
	 * 
	 * @author Filip
	 *
	 */
	public static class MultistackEntry {
		/**
		 * Omotač.
		 */
		ValueWrapper value;
		/**
		 * Referenca na sljedeći element.
		 */
		MultistackEntry next;

		/**
		 * Konstruktor, prima omotač i referencu na sljedeći element.
		 * 
		 * @param value
		 *            omotač
		 * @param next
		 *            referenca na sljedeći element
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Metoda vraća vrijednost omotača.
		 * 
		 * @return vrijednost omotača
		 */
		public ValueWrapper getValue() {
			return value;
		}

		/**
		 * Metoda vraća sljedeći element liste.
		 * 
		 * @return sljedeći element liste
		 */
		public MultistackEntry getNext() {
			return next;
		}
	}
}
