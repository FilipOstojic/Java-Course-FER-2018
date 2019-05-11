package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testovi za razred <code>ValueWrapper</code>. Testovi se većinom temelje na
 * povjerama rezultata i tipova tijekom i nakon računskih operacija.
 * 
 * @author Filip
 *
 */
public class ValueWrapperTest {

	@Test
	public void testConstructor() {
		new ValueWrapper("5.5");
		new ValueWrapper("5.5E2");
		new ValueWrapper("5");

		new ValueWrapper(1.2E3);
		new ValueWrapper(2.0);
		new ValueWrapper(2);
	}

	@Test
	public void testConstructor2() {
		new ValueWrapper(null);
		new ValueWrapper(Integer.valueOf(3));
		new ValueWrapper(Double.valueOf("2.4"));
		new ValueWrapper("Filip"); // prihvaća, ali kod operacija će baciti iznimku
		new ValueWrapper(Long.valueOf("2"));  // prihvaća, ali kod operacija će baciti iznimku
	}

	@Test
	public void testGetValue() {
		ValueWrapper wraper = new ValueWrapper("5.5");
		assertEquals(wraper.getValue(), "5.5");

		wraper.add(0.5);
		assertEquals(wraper.getValue(), 6.0); // promjena tipa iz String-a u Double
	}

	@Test
	public void testGetValue2() {
		ValueWrapper wraper = new ValueWrapper(null);
		assertEquals(wraper.getValue(), null);

		wraper.add("0.5");
		assertEquals(wraper.getValue(), 0.5); // promjena tipa iz null reference u Integer pa u Double
	}

	@Test
	public void testGetValue3() {
		ValueWrapper wraper1 = new ValueWrapper(5);
		ValueWrapper wraper2 = new ValueWrapper("2");
		assertEquals(wraper1.getValue(), 5);

		wraper1.add(wraper2.getValue());
		assertEquals(wraper1.getValue(), 7);
		assertEquals(wraper2.getValue(), "2"); // value od wrapper2 je i dalje ne promjenjena
	}

	@Test
	public void testAddIntegers() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.add("1");

		assertEquals(2, wrapper.getValue());
		assertEquals("Integer", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testAddIntegerAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.add("2.0");

		assertEquals(3.0, wrapper.getValue());
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testAddDoubleAndInteger() {
		ValueWrapper wrapper = new ValueWrapper(1.5);
		wrapper.add(1);

		assertEquals(2.5, wrapper.getValue());
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testAddDoubles() {
		ValueWrapper wrapper = new ValueWrapper(1.5);
		wrapper.add(3.5);

		assertEquals(5.0, wrapper.getValue());
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testSubtractInegerWithDouble() {
		ValueWrapper wrapper1 = new ValueWrapper("5");
		ValueWrapper wrapper2 = new ValueWrapper("1E1");
		wrapper1.subtract(wrapper2.getValue());

		assertEquals(-5.0, wrapper1.getValue());
		assertEquals("Double", wrapper1.getValue().getClass().getSimpleName());
	}

	@Test
	public void testSubtractIntegers() {
		ValueWrapper wrapper1 = new ValueWrapper("5");
		ValueWrapper wrapper2 = new ValueWrapper("-3");
		wrapper1.subtract(wrapper2.getValue());

		assertEquals(8, wrapper1.getValue());
		assertEquals("Integer", wrapper1.getValue().getClass().getSimpleName());
	}

	@Test
	public void testMultiply() {
		ValueWrapper wrapper = new ValueWrapper("5");
		wrapper.multiply("2");

		assertEquals(10, wrapper.getValue());
		assertEquals("Integer", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testMultiplyWithDouble() {
		ValueWrapper wrapper = new ValueWrapper("-5.1");
		wrapper.multiply(3.0);

		assertEquals((double) wrapper.getValue(), -15.3, 0.0001);
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testMultiplyWithNull() {
		ValueWrapper wrapper = new ValueWrapper("5");
		wrapper.multiply(null);

		assertEquals(0, wrapper.getValue());
		assertEquals("Integer", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testMultiplyNullWithNull() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.multiply(null);

		assertEquals(0, wrapper.getValue());
		assertEquals("Integer", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testDivide() {
		ValueWrapper wrapper = new ValueWrapper(10.0);
		wrapper.divide(2);

		assertEquals(5.0, wrapper.getValue());
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testDivideIntegers() {
		ValueWrapper wrapper = new ValueWrapper(5);
		wrapper.divide(2);

		assertEquals(2, wrapper.getValue());
		assertEquals("Integer", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testDivideIntegerWithDouble() {
		ValueWrapper wrapper = new ValueWrapper(5);
		wrapper.divide(2.0);

		assertEquals(2.5, wrapper.getValue());
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDivideWithZero() {
		ValueWrapper wrapper = new ValueWrapper(10.0);
		wrapper.divide(0);

		assertEquals(0.0, wrapper.getValue());
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDivideWithNull() {
		ValueWrapper wrapper = new ValueWrapper(10.0);
		wrapper.divide(null);

		assertEquals(5.0, wrapper.getValue());
		assertEquals("Double", wrapper.getValue().getClass().getSimpleName());
	}

	@Test
	public void testCompareNullAndNull() {
		ValueWrapper wrapper1 = new ValueWrapper(null);

		assertEquals(0, wrapper1.numCompare(null)); // null == null
		assertEquals(null, wrapper1.getValue()); // još uvijek je vrijednost null
	}

	@Test
	public void testCompareNullAndOne() {
		ValueWrapper wrapper1 = new ValueWrapper(null);

		assertEquals(0, wrapper1.numCompare(0));
		assertEquals(0, wrapper1.getValue()); // vrijednost null se promjenila na 0
	}

	@Test
	public void testCompareIntegerAndString() {
		ValueWrapper wrapper1 = new ValueWrapper("1");

		assertEquals(wrapper1.numCompare(1), 0);
	}

	@Test
	public void testCompareDoubles() {
		ValueWrapper wrapper1 = new ValueWrapper("1.5");
		ValueWrapper wrapper2 = new ValueWrapper("1.5");

		assertEquals(wrapper1.numCompare(1.5), 0);
		assertEquals(wrapper1.getValue(), (double) 1.5);
		assertNotEquals(wrapper1.getValue(), wrapper2.getValue()); // wrapper1 se castao iz Stringa u double, dok je
																	// wrapper2 ostao String
	}

	@Test
	public void testCompareIntegerAndDouble() {
		ValueWrapper wrapper1 = new ValueWrapper(1);
		ValueWrapper wrapper2 = new ValueWrapper(1.0);
		wrapper1.add(0.0);

		assertEquals(wrapper1.getValue(), (double) 1.0);
		assertEquals(wrapper1.getValue(), wrapper2.getValue()); // wrapper1 se castao iz Integera u Double, dok je
																// wrapper2 ostao Double
	}

}
