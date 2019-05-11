package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.Test;

import static hr.fer.zemris.java.hw02.ComplexNumber.*;
import static java.lang.Math.PI;

/**
 * Razred <code>ComplexNumberTest</code> testira metode
 * <code>ComplexNumber</code> razreda.
 * @author Filip Ostojić
 *
 */
public class ComplexNumberTest {

	@Test
	public void testFromReal() {
		Assert.assertEquals(fromReal(5), new ComplexNumber(5, 0));
	}
	
	@Test
	public void testParse1() {
		Assert.assertEquals(ComplexNumber.parse("-i"), new ComplexNumber(0,-1));
		Assert.assertEquals(ComplexNumber.parse("i"), new ComplexNumber(0,1));
	}

	@Test
	public void testParse2() {
		Assert.assertEquals(ComplexNumber.parse("6i"), new ComplexNumber(0,6));
		Assert.assertEquals(ComplexNumber.parse("-3.3i"), new ComplexNumber(0,-3.3));
		Assert.assertEquals(ComplexNumber.parse("-3i"), new ComplexNumber(0,-3));
	}

	@Test
	public void testParse3() {
		Assert.assertEquals(ComplexNumber.parse("5.5+5i"), new ComplexNumber(5.5,5));
		Assert.assertEquals(ComplexNumber.parse("1.2-1.1i"), new ComplexNumber(1.2,-1.1));
		Assert.assertEquals(ComplexNumber.parse("2-i"), new ComplexNumber(2,-1));
		Assert.assertEquals(ComplexNumber.parse("   2  +   i"), new ComplexNumber(2,1));
	}

	@Test
	public void testParse4() {
		Assert.assertEquals(ComplexNumber.parse("15"), new ComplexNumber(15,0));
		Assert.assertEquals(ComplexNumber.parse("-8"), new ComplexNumber(-8, 0));
	}
	
	@Test
	public void testParse5() {
		Assert.assertEquals(ComplexNumber.parse("5.26"), new ComplexNumber(5.26,0));
		Assert.assertEquals(ComplexNumber.parse("-10.2"), new ComplexNumber(-10.2,0));
	}
	
	@Test
	public void testGetters() {
		ComplexNumber cn1 = new ComplexNumber(0, 1);

		Assert.assertEquals(cn1.getMagnitude(), 1.0,0.0001);
		Assert.assertEquals(cn1.getAngle(), PI/2.0, 0.0001);
		Assert.assertEquals(cn1.getReal(), 0.0, 0.0001);
		Assert.assertEquals(cn1.getImaginary(), 1.0, 0.0001);
	}
	
	@Test
	public void testAdd() {
		ComplexNumber cn1 = ComplexNumber.parse("2+2i");
		ComplexNumber cn2 = ComplexNumber.parse("2+i");
		
		Assert.assertEquals(cn1.add(cn2), new ComplexNumber(4, 3));
	}
	
	@Test
	public void testSub() {
		ComplexNumber cn1 = ComplexNumber.parse("2+2i");
		ComplexNumber cn2 = ComplexNumber.parse("2+i");
		
		Assert.assertEquals(cn1.sub(cn2), new ComplexNumber(0, 1));
	}
	
	@Test
	public void testMul() {
		ComplexNumber cn1 = ComplexNumber.parse("2+2i");
		ComplexNumber cn2 = ComplexNumber.parse("2+i");
		
		Assert.assertEquals(cn1.mul(cn2), new ComplexNumber(2, 6));
	}
	
	@Test
	public void testDiv() {
		ComplexNumber cn1 = ComplexNumber.parse("2+2i");
		ComplexNumber cn2 = ComplexNumber.parse("2-i");
		
		Assert.assertEquals(cn1.div(cn2), new ComplexNumber(2.0/5.0, 6.0/5.0));
	}
	
	@Test
	public void testPower() {
		ComplexNumber cn1 = ComplexNumber.parse("2+2i");
		
		Assert.assertEquals(cn1.power(3), new ComplexNumber(-16, 16));
	}
	
	@Test
	public void testRoots() {
		ComplexNumber cn1 = ComplexNumber.parse("2+2i");
		
		ComplexNumber[] expectedResult = new ComplexNumber[3];
		expectedResult[0] = new ComplexNumber(1.3660, 0.3660);
		expectedResult[1] = new ComplexNumber(-1.00, 1.00);
		expectedResult[2] = new ComplexNumber(-0.3660, -1.3660);
		
		ComplexNumber[] result = cn1.root(3);
		
		//provjera se vršila tako da uspoređujem realni i realni te imaginarni i imaginarni dio od svakog broja
		
		Assert.assertEquals((double) expectedResult[0].getReal(), (double) result[0].getReal(), 0.001);
		Assert.assertEquals((double) expectedResult[0].getImaginary(), (double) result[0].getImaginary(), 0.001);
		
		Assert.assertEquals((double) expectedResult[1].getReal(), (double) result[1].getReal(), 0.001);
		Assert.assertEquals((double) expectedResult[1].getImaginary(), (double) result[1].getImaginary(), 0.001);
		
		Assert.assertEquals((double) expectedResult[2].getReal(), (double) result[2].getReal(), 0.001);
		Assert.assertEquals((double) expectedResult[2].getImaginary(), (double) result[2].getImaginary(), 0.001);
	}
	
	@Test
	public void testEquals() {
		ComplexNumber cn1 = ComplexNumber.parse("5+5i");
		
		Assert.assertEquals(cn1, new ComplexNumber(5, 5));
	}
}
