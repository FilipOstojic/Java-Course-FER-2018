package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;
import static hr.fer.zemris.java.hw05.db.FieldValueGetters.*;
import static hr.fer.zemris.java.hw05.db.ComparisonOperators.*;

public class ConitionalExpressionTest {

	@Test
	public void test() {
		ConditionalExpression expr = new ConditionalExpression(LAST_NAME, "Bos*", LIKE);
		
		StudentRecord record = new StudentRecord("123456", "Tarzan", "Bosančić", "4");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record), expr.getLiteral());
		assertTrue(recordSatisfies);
		
		StudentRecord record2 = new StudentRecord("98765", "Marijo", "Super", "5");
		boolean recordSatisfies2 = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record2), expr.getLiteral());
		assertFalse(recordSatisfies2);
	}
	
	@Test
	public void test2() {
		ConditionalExpression expr = new ConditionalExpression(FIRST_NAME, "T*n", LIKE);
		
		StudentRecord record = new StudentRecord("123456", "Tarzan", "Bosančić", "4");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record), expr.getLiteral());
		assertTrue(recordSatisfies);
		
		StudentRecord record2 = new StudentRecord("98765", "Marijo", "Super", "5");
		boolean recordSatisfies2 = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record2), expr.getLiteral());
		assertFalse(recordSatisfies2);
	}
	
	@Test
	public void test3() {
		ConditionalExpression expr = new ConditionalExpression(JMBAG, "012345", GREATER);
		
		StudentRecord record = new StudentRecord("123456", "Tarzan", "Bosančić", "4");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record), expr.getLiteral());
		assertTrue(recordSatisfies);
		
		StudentRecord record2 = new StudentRecord("98765", "Marijo", "Super", "5");
		boolean recordSatisfies2 = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record2), expr.getLiteral());
		assertTrue(recordSatisfies2);
	}

}
