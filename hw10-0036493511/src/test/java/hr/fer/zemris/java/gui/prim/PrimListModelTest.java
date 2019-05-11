package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Osnovni testovi za ispravan rad {@link PrimListModel}-a.
 * 
 * @author Filip
 *
 */
public class PrimListModelTest {

	@Test
	public void testNextPrime() {
		PrimListModel model = new PrimListModel();

		model.next();
		model.next();
		model.next();

		assertEquals(Integer.valueOf(2), model.getElementAt(0));
		assertEquals(Integer.valueOf(3), model.getElementAt(1));
		assertEquals(Integer.valueOf(5), model.getElementAt(2));
	}

	@Test
	public void testPrimeListModelSize() {
		PrimListModel model = new PrimListModel();

		model.next();
		model.next();
		model.next();
		model.next();

		assertEquals(4, model.getSize());
	}
	
	@Test
	public void testInitialSize() {
		PrimListModel model = new PrimListModel();
		
		assertEquals(model.getSize(), 0);
	}
}
