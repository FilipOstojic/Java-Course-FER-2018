package hr.fer.zemris.java.hw05.collections;

import static org.junit.Assert.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class SimpleHashtableTest {

	// 1st TASK TESTS
	
	@Test
	public void testConstructors() {
		SimpleHashtable<String, Integer> sht1 = new SimpleHashtable<>();
		assertEquals(16, sht1.getLentgth());
		assertEquals(0, sht1.size());
		
		SimpleHashtable<String, Integer> sht2 = new SimpleHashtable<>(2);
		assertEquals(2, sht2.getLentgth());
		assertEquals(0, sht2.size());
	}

	@Test
	public void testExampleFromHW() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Assert.assertEquals(5, (int) examMarks.get("Kristina"));
		Assert.assertEquals(4, examMarks.size());

		// System.out.println(examMarks.toString());
	}

	@Test
	public void testRemove() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Assert.assertEquals(4, examMarks.size());
		examMarks.remove("Jasna");
		Assert.assertEquals(3, examMarks.size());
		examMarks.remove("Ante");
		Assert.assertEquals(2, examMarks.size());
		examMarks.remove("Kristina");
		Assert.assertEquals(1, examMarks.size());
		examMarks.remove("Ivana");
		Assert.assertEquals(0, examMarks.size());

		Assert.assertEquals(true, examMarks.isEmpty());
	}

	@Test
	public void testRemove2() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		examMarks.remove("Ivo"); // even though an element does not exist, does not throw exception

	}

	@Test
	public void testContains() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Assert.assertEquals(true, examMarks.containsKey("Ivana"));
		Assert.assertEquals(false, examMarks.containsKey("Marko"));

		Assert.assertEquals(true, examMarks.containsValue(5));
		Assert.assertEquals(false, examMarks.containsValue(1));
	}

	@Test
	public void testGet() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);

		Assert.assertEquals(2, (int) examMarks.get("Ivana"));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGet2() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);

		Assert.assertEquals(null, examMarks.get("Marko"));
		// this type of test throws an exception (and does not return null) because
		// there can be TableEntry with existing key which has value null
	}

	// 2nd TASK TESTS

	@Test
	public void testResize() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		Assert.assertEquals(2, examMarks.getLentgth());

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		examMarks.put("Filip", 5);

		Assert.assertEquals(5, examMarks.size());
		Assert.assertEquals(8, examMarks.getLentgth());
	}

	@Test
	public void testClear() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		examMarks.put("Filip", 5);

		Assert.assertEquals(5, examMarks.size());
		examMarks.clear();

		Assert.assertEquals(0, examMarks.size());

		Assert.assertEquals(false, examMarks.containsKey("Filip"));
		Assert.assertEquals(false, examMarks.containsKey("Ivana"));
		Assert.assertEquals(false, examMarks.containsKey("Ante"));
		Assert.assertEquals(false, examMarks.containsKey("Kristina"));
		Assert.assertEquals(false, examMarks.containsKey("Jasna"));

		Assert.assertEquals(8, examMarks.getLentgth());
		// System.out.println(examMarks.toString());
	}

	// 3rd TASK TESTS

	@Test(expected = ConcurrentModificationException.class)
	public void testWrongModifying() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}
	
	@Test
	public void testCorrectModifying() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		
		Assert.assertEquals(0, examMarks.size());
	}
}
