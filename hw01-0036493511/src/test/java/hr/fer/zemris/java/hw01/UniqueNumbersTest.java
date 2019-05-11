package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;;

/**
 * Razred <code>UniqueNumbersTest</code> sadrži šest testova
 * kojima se testiraju metode: 'addNode(TreeNode glava, int x)' ,
 * 'treeSize(TreeNode glava)' i 'containsValue(TreeNode glava, int x)'
 * rezreda <code>UniqueNumbers</code>.
 * @author Filip Ostojić
 * @version 1.0
 *
 */
public class UniqueNumbersTest {
	
	/**
	 * Metoda koja služi za inicijalizaciju stabla.
	 * @return vraća korijen stabla
	 */
	public TreeNode setTree() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		return glava;
	}
	
	/**
	 * Metoda koja služi za inicijalizaciju praznog stabla.
	 * @return vraća korijen stabla
	 */
	public TreeNode emptyTree() {
		TreeNode glava = null;
		return glava;
	}
	
	/**
	 * Metoda testira veličinu stabla i činjenicu da se ne dodaju isti elementi u stablo.
	 */
	@Test
	public void testTreeSize() {
		Assert.assertEquals(5, UniqueNumbers.treeSize(setTree()));
	}
	
	/**
	 * Metoda testira pravilno sortiranje unutr stabla.
	 */
	@Test
	public void testSpecificNode() {
		Assert.assertEquals(35, setTree().left.right.value);
	}
	
	/**
	 * Metoda testira dobro pozicioniranje korijena stabla.
	 */
	@Test
	public void testRoot() {
		Assert.assertEquals(42, setTree().value);
	}
	
	/**
	 * Metoda testira postojanje elementa u stablu.
	 */
	@Test
	public void testExistingElement() {
		Assert.assertTrue(containsValue(setTree(), 76));
	}
	
	/**
	 * Metoda testira ne postojanje elementa u stablu.
	 */
	@Test
	public void testNonexistingElement() {
		Assert.assertFalse(containsValue(setTree(), 75));
	}
	
	/**
	 * Metoda testira veličinu praznog stabla.
	 */
	@Test
	public void testEmptyTreeSize() {
		Assert.assertEquals(0, treeSize(emptyTree()));
	}
}
