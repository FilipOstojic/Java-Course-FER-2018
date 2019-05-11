package hr.fer.zemris.math;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * Razred <code>Vector3Test</code> sadrži osnovne testove za računske operacije
 * s vektorima.
 * 
 * @author Filip
 *
 */
public class Vector3Test {

	Vector3 vector1;
	Vector3 vector2;
	Vector3 vector3;

	private static double treshold = 10E-4;

	@Before
	public void setUp() {
		vector1 = new Vector3(0, 0, 0);
		vector2 = new Vector3(-15.5, 9.2, -3.5);
		vector3 = null;
	}

	@Test
	public void testNorm() {
		assertEquals(vector1.norm(), 0, treshold);
		assertEquals(vector2.norm(), 18.36137, treshold);
	}

	@Test(expected = NullPointerException.class)
	public void testNorm2() {
		assertEquals(vector3.norm(), 0, treshold);
	}

	@Test
	public void testAdd() {
		assertEquals(vector1.add(new Vector3(-1, 0, 1)), new Vector3(-1, 0, 1));
		assertEquals(vector2.add(new Vector3(-1, 0, 1)), new Vector3(-16.5, 9.2, -2.5));
	}

	@Test(expected = NullPointerException.class)
	public void testAdd2() {
		assertEquals(vector3.add(new Vector3(-1, 0, 1)), new Vector3(-1, 0, 1));
	}

	@Test
	public void testNormalized() {
		assertEquals(vector1.normalized(), vector1);
		assertEquals(vector2.normalized(), vector2.scale(1.0 / vector2.norm()));
	}

	@Test(expected = NullPointerException.class)
	public void testNormalized2() {
		assertEquals(vector3.normalized(), new Vector3(0, 0, 0));
	}

	@Test
	public void testSub() {
		assertEquals(vector1.sub(new Vector3(-1, 0, 1)), new Vector3(1, 0, -1));
		assertEquals(vector2.sub(new Vector3(-1, 0, 1)), new Vector3(-14.5, 9.2, -4.5));
	}

	@Test(expected = NullPointerException.class)
	public void testSub2() {
		assertEquals(vector3.sub(new Vector3(-1, 0, 1)), new Vector3(-1, 0, 1));
	}

	@Test
	public void testDot() {
		assertEquals(vector1.dot(new Vector3(-1, 0, 1)), 0.0, treshold);
		assertEquals(vector2.dot(new Vector3(-1, 0, 1)), 12.0, treshold);
	}

	@Test(expected = NullPointerException.class)
	public void testDot2() {
		assertEquals(vector3.dot(new Vector3(-1, 0, 1)), new Vector3(-1, 0, 1));
	}

	@Test
	public void testCross() {
		assertEquals(vector1.cross(new Vector3(-1, 0, 1)), new Vector3(0, 0, 0));
		assertEquals(vector2.cross(new Vector3(-1, 0, 1)), new Vector3(9.2, 19, 9.2));
	}

	@Test(expected = NullPointerException.class)
	public void testCross2() {
		assertEquals(vector3.cross(new Vector3(-1, 0, 1)), new Vector3(-1, 0, 1));
	}

	@Test
	public void testScale() {
		assertEquals(vector1.scale(5), new Vector3(0, 0, 0));
		assertEquals(vector2.scale(0), new Vector3(0, 0, 0));
	}

	@Test(expected = NullPointerException.class)
	public void testScale2() {
		assertEquals(vector3.scale(-13), new Vector3(-1, 0, 1));
	}

	@Test
	public void testCosAngle() {
		assertEquals(vector1.cosAngle(vector2), 0, treshold);
		assertEquals(vector2.cosAngle(new Vector3(12, -3, 4)), -0.95350, treshold);
	}

	@Test(expected = NullPointerException.class)
	public void testCosAngle2() {
		assertEquals(vector3.cosAngle(vector2), new Vector3(-1, 0, 1));
	}

	@Test
	public void testToArray() {
		assertTrue(Arrays.equals(new double[] { 0, 0, 0 }, vector1.toArray()));
	}
}
