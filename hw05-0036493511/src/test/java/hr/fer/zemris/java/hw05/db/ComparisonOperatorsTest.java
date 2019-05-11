package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLESS() {
		IComparisonOperator oper = ComparisonOperators.LESS;

		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ara"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void testLESSEQ() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;

		assertTrue(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ara"));
		assertFalse(oper.satisfied("Ana", "Aba"));
	}

	@Test
	public void testGREATER() {
		IComparisonOperator oper = ComparisonOperators.GREATER;

		assertTrue(oper.satisfied("Mirko", "Ivo"));
		assertTrue(oper.satisfied("Filip", "Anastazije"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void testGREATEREQ() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;

		assertTrue(oper.satisfied("Kiki", "Kiki"));
		assertTrue(oper.satisfied("Miro", "Ante"));
		assertFalse(oper.satisfied("Karla", "Oprah"));
	}

	@Test
	public void testEQUALS() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;

		assertTrue(oper.satisfied("Kiki", "Kiki"));
		assertTrue(oper.satisfied("Željka", "Željka"));
		assertFalse(oper.satisfied("Karla", "Karlo"));
	}

	@Test
	public void testNOTEQUALS() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;

		assertTrue(oper.satisfied("Kiki", "Mara"));
		assertTrue(oper.satisfied("Željka", "Mile"));
		assertFalse(oper.satisfied("Bruno", "Bruno"));
	}

	@Test
	public void testLIKE() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertTrue(oper.satisfied("Zagreb", "Zag*"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	public void testLIKE2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertTrue(oper.satisfied("običanString", "*"));
		assertTrue(oper.satisfied("običanString", "o*ng"));
	}
}
