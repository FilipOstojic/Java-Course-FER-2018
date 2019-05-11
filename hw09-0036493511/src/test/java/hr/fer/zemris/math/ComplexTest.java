package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Razred <code>ComplexTest</code> sadrži osnovne testove za računske operacije
 * kompleksnih brojeva.
 * 
 * @author Filip
 *
 */
public class ComplexTest {
	
	private static double treshold = 0.0001;
	
	@Test
	public void negate() {
		Complex c = new Complex(0,0);
		assertEquals(c, c.negate());
	}
	
	@Test
	public void negate2() {
		Complex c = new Complex(13,-2);
		assertEquals(c.negate(), new Complex(-13, 2));
	}
	
	@Test
	public void testAdd() {
		Complex cn1 = new Complex(2, 2);
		Complex cn2 = new Complex(-2,-1);
		
		assertEquals(cn1.add(cn2), new Complex(0, 1));
	}
	
	@Test
	public void testAdd2() {
		Complex cn1 = new Complex(2.5, 0);
		Complex cn2 = new Complex(-0,-1);
		
		assertEquals(cn1.add(cn2), new Complex(2.5, -1));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAdd3() {
		Complex cn1 = new Complex(2, 2);
		Complex cn2 = null;
		
		assertEquals(cn1.add(cn2), new Complex(2,2));
	}
	
	@Test
	public void testSub() {
		Complex cn1 = new Complex(2, 2);
		Complex cn2 = new Complex(2, 1);
		
		assertEquals(cn1.sub(cn2), new Complex(0, 1));
	}
	
	@Test
	public void testSub2() {
		Complex cn1 = new Complex(0, 3.14);
		Complex cn2 = new Complex(3.14, 0);
		
		assertEquals(cn1.sub(cn2), new Complex(-3.14, 3.14));
	}
	
	@Test
	public void testMultiply() {
		Complex cn1 = new Complex(2, 2);
		Complex cn2 = new Complex(2, 0);
		
		assertEquals(cn1.multiply(cn2), new Complex(4, 4));
	}
	
	@Test
	public void testMul2() {
		Complex cn1 = new Complex(25, -3.5);
		Complex cn2 = new Complex(3.5, -1.5);
		
		assertEquals(cn1.multiply(cn2), new Complex(82.25, -49.75));
	}
	
	@Test
	public void testDiv() {
		Complex cn1 = new Complex(2, 2);
		Complex cn2 = new Complex(2, -1);
		
		assertEquals(cn1.div(cn2), new Complex(2.0/5.0, 6.0/5.0));
	}
	
	@Test
	public void testDiv2() {
		Complex cn1 = new Complex(2.5, 3.2);
		Complex cn2 = new Complex(0.2, 5);
		
		assertEquals(cn1.div(cn2).getReal(), 825./1252. , treshold);
		assertEquals(cn1.div(cn2).getImaginary(), -593./1252 , treshold);
	}
	
	@Test
	public void testPower() {
		Complex cn1 = new Complex(2, 2);
		
		assertEquals(cn1.power(3), new Complex(-16, 16));
	}
	
	@Test
	public void testRoots() {
		Complex cn1 = new Complex(2, 2);
		
		List<Complex> expectedResult = new ArrayList<>();
		expectedResult.add(new Complex(1.3660, 0.3660));
		expectedResult.add(new Complex(-1.00, 1.00));
		expectedResult.add(new Complex(-0.3660, -1.3660));
		
		List<Complex> result = cn1.root(3);
		
		int i = 0;
		for (Complex c : expectedResult) {
			assertEquals(c.getReal(), result.get(i).getReal(), 0.001);
			assertEquals(c.getImaginary(), result.get(i++).getImaginary(), 0.001);
		}
	}

}
