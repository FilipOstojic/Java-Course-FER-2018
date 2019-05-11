package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * U ovom razredu se nalaze neki osnovni testovi za sve metode razreda
 * <code>Dictionary</code>.
 * 
 * @author Filip
 *
 */
public class DictionaryTest {

	public Dictionary getDictionary() {
		Dictionary dictionary = new Dictionary();
		dictionary.put("HarryPotter", "JKRowling");
		dictionary.put("TheLordOfTheRings", "JRRTolkien");
		dictionary.put("TheSilmarillion", "JRRTolkien");
		dictionary.put("TheAlchemist", "PauloCoelho");
		dictionary.put("TheDaVinciCode", "DanBrown");
		dictionary.put("AngelsAndDeamons", "DanBrown");
		dictionary.put("DigitalFortress", "DanBrown");

		return dictionary;
	}

	@Test
	public void testEmptyDictionary() {
		Dictionary dictionary = new Dictionary();

		assertTrue(dictionary.isEmpty());
		assertEquals(0, dictionary.size());
	}

	@Test
	public void testPut() {
		Dictionary dictionary = new Dictionary();
		dictionary.put("Marko", 2);
		dictionary.put("Slavko", 3);
		dictionary.put("Darko", 4);
		dictionary.put("Marko", 5);
		dictionary.put("Slavko", 4);

		assertEquals(3, dictionary.size());

		assertEquals(5, dictionary.get("Marko"));
		assertEquals(4, dictionary.get("Slavko"));
		assertEquals(4, dictionary.get("Darko"));
	}

	@Test
	public void testClear() {
		Dictionary dictionary = getDictionary();
		assertEquals(7, dictionary.size());
		
		dictionary.clear();
		assertEquals(0, dictionary.size());
	}

	@Test
	public void testGet() {
		Dictionary dictionary = getDictionary();

		assertEquals("DanBrown", dictionary.get("DigitalFortress"));
		assertEquals("JRRTolkien", dictionary.get("TheSilmarillion"));
		assertEquals(null, dictionary.get("Inferno"));

	}

}
