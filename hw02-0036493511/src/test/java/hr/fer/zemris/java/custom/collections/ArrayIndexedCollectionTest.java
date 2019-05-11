package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Razred sadrži desetak tesova kojim se se testiraju metode razreda
 * <code>ArrayIndexedCollection</code>.
 * @author Filip Ostojić
 *
 */
public class ArrayIndexedCollectionTest {
	
	@Test
	public void testConsturctor1() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		Assert.assertEquals(0, col.size());
	}
	
	@Test
	public void testConsturctor3() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		col.add("štefica");
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col);
		Assert.assertEquals(2, col2.size());
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		
		col.add(10);
		col.add(15);
		col.add(38);
		
		Assert.assertEquals(3, col.size());
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Marko");
		col.add("Darko");
		col.add("Žarko");
		
		Assert.assertEquals("Marko", col.get(0));
		Assert.assertEquals("Žarko", col.get(2));
	}
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Marko");
		col.add("Darko");
		col.clear();
		Assert.assertEquals(0, col.size());
	}
	
	@Test
	public void insertTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.insert(4, 0);
		col.insert(3, 0);
		col.insert(2, 0);
		col.insert(1, 0);
		col.insert(0, 0);
		
		Assert.assertEquals(0, col.get(0));
		Assert.assertEquals(1, col.get(1));
		Assert.assertEquals(4, col.get(4));
	}
	
	@Test
	public void indexOfTest() {	
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(10);
		col.add(20);
		col.add(30);
		
		Assert.assertEquals(-1, col.indexOf(-10));
		Assert.assertEquals(1, col.indexOf(20));
	}
	
	@Test
	public void removeTest() {	
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(10);
		col.add(20);
		col.add(30);
		
		col.remove(1);
		
		Assert.assertEquals(10, col.get(0));
		Assert.assertEquals(30, col.get(1));
	}
	
	@Test
	public void containsTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(5.5);
		col.add(10);
		
		Assert.assertEquals(col.contains(10), true);
	}
	
	@Test
	public void addAllTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		
		col.add(1);
		col.add(2);
		col2.add(3);
		col2.add(4);
		
		col.addAll(col2);
		
		Assert.assertEquals(3, col.get(2));
		Assert.assertEquals(4, col.get(3));
	}
	
	@Test
	public void resize() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		for (int i = 0; i< 20 ; i++) {
			col.add(i);
		}
		Assert.assertEquals(col.size(), 20);
	}
}
