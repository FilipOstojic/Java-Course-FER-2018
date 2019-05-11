package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.factorial;

import org.junit.Assert;
import org.junit.Test;

/**
 * razred <code> FactorialTest </code> sadrži tri 
 * metode (testa) za provjeru izračuna faktorijela određenih slučajeva
 * 
 * @author Filip
 * @version 1.0
 */
public class FactorialTest {	
	
	/**
	 * metoda testira slučaj unosa negativne nule
	 */
	@Test
	public void testNegativeZero() {
		try {
			Assert.assertEquals(1, factorial(-0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * metoda testira slučaj unosa nule
	 */
	@Test
	public void testZero() {
		try {
			Assert.assertEquals(1, factorial(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * metoda testira izračun faktorijela broja 5
	 * (konkretnu formulu izračuna)
	 */
	@Test
	public void testNumber() {
		try {
			Assert.assertEquals(120, factorial(5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * metoda testira slučaj unosa negativnog broja te bacanja iznimke
	 */
	@Test
	public void testFactorialThrowsException() {
		boolean thrown = false;
		try {
			factorial(-5);
		} catch (Exception e) {
			thrown = true;
		}
		Assert.assertTrue(thrown);
	}
	
}
