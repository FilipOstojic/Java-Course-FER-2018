package hr.fer.zemris.math;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * U ovom razredu se nalaze neki osnovni testovi za sve metode razreda
 * <code>Vector2D</code>.
 * 
 * @author Filip
 *
 */
public class Vector2DTest {

	@Test
	public void testGettersAndEquals() {
		Vector2D vector = new Vector2D(2.0, -3.5);

		assertEquals(vector.getX(), 2.0, 0.0001);
		assertEquals(vector.getY(), -3.5, 0.0001);

		Vector2D vector2 = new Vector2D(2, -3.5);

		assertEquals(vector, vector2);
	}

	@Test
	public void testTraslate() {

		Vector2D vector1 = new Vector2D(2, 2);
		vector1.translate(new Vector2D(0, 2));
		assertEquals(vector1, new Vector2D(2, 4));

		Vector2D vector2 = new Vector2D(-2.5, 3);
		vector2.translate(new Vector2D(1, 2));
		assertEquals(vector2, new Vector2D(-1.5, 5));
	}

	@Test
	public void testTraslated() {

		Vector2D vector1 = new Vector2D(2, 2);
		Vector2D vector2 = vector1.translated(new Vector2D(0, 2));
		assertEquals(vector2, new Vector2D(2, 4));

		Vector2D vector3 = new Vector2D(-10.2, 3.1);
		Vector2D vector4 = vector3.translated(new Vector2D(-1, 2));
		assertEquals(vector4, new Vector2D(-11.2, 5.1));
	}

	@Test
	public void testRotate() {
		// prvi kvadrant
		Vector2D vector1 = new Vector2D(2, 2);
		vector1.rotate(45);
		assertEquals(vector1, new Vector2D(0, Math.sqrt(8)));

		// drugi kvadrant
		Vector2D vector2 = new Vector2D(-2, 2);
		vector2.rotate(45);
		assertEquals(vector2, new Vector2D(-Math.sqrt(8), 0));

		// treci kvadrant
		Vector2D vector3 = new Vector2D(-2, -2);
		vector3.rotate(45);
		assertEquals(vector3, new Vector2D(0, -Math.sqrt(8)));

		// treci kvadrant
		Vector2D vector4 = new Vector2D(2, -2);
		vector4.rotate(45);
		assertEquals(vector4, new Vector2D(Math.sqrt(8), 0));

	}

	@Test
	public void testRotateNegative() {

		Vector2D vector1 = new Vector2D(5, 8.6602540378);
		vector1.rotate(-60);
		assertEquals(vector1, new Vector2D(Math.sqrt(100), 0));

		Vector2D vector2 = new Vector2D(1, 1);
		vector2.rotate(-45);
		assertEquals(vector2, new Vector2D(Math.sqrt(2), 0));

		Vector2D vector3 = new Vector2D(-1, -1);
		vector3.rotate(-45);
		assertEquals(vector3, new Vector2D(-Math.sqrt(2), 0));

	}

	@Test
	public void testRotated() {
		Vector2D vector1 = new Vector2D(5, 5);
		Vector2D vector2 = vector1.rotated(360);
		assertEquals(vector1, vector2);

		Vector2D vector3 = new Vector2D(2, 2);
		Vector2D vector4 = vector3.rotated(-225);
		assertEquals(vector4, new Vector2D(-Math.sqrt(8), 0));

	}

	@Test
	public void testScale() {
		Vector2D vector1 = new Vector2D(3, -5);
		vector1.scale(0.5);
		assertEquals(vector1, new Vector2D(1.5, -2.5));

		Vector2D vector2 = new Vector2D(3, 4);
		vector2.scale(2);
		assertEquals(vector2, new Vector2D(6, 8));

	}

	@Test
	public void testScaled() {
		Vector2D vector1 = new Vector2D(5, 5);
		Vector2D vector2 = vector1.scaled(0.2);
		assertEquals(vector2, new Vector2D(1, 1));

	}

	@Test
	public void testCopy() {
		Vector2D vector1 = new Vector2D(-8.3, 2.4);
		Vector2D vector2 = vector1.copy();
		assertEquals(vector2, vector1);
	}

}
