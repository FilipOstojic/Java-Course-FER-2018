package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class QueryParserTest {
	
	QueryParser parser;
	
	@Test
	public void testIsDirectQuery() {
		parser = new QueryParser("lastName LIKE \"B*\"");
		assertFalse(parser.isDirectQuery());
	}

	@Test
	public void testIsDirectQuery2() {
		parser = new QueryParser("jmbag = \"0123456789\"");
		assertTrue(parser.isDirectQuery());
	}
	
	@Test
	public void testGetQueriedJMBAG() {
		parser = new QueryParser("jmbag = \"0123456789\"");
		assertEquals("0123456789", parser.getQueriedJMBAG());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetQueriedJMBAGException() {
		parser = new QueryParser("jmbag = \"0123456789\" and lastName LIKE \"B*\"");
		assertEquals("0123456789", parser.getQueriedJMBAG());
	}
	
	@Test
	public void testGetQuery() {
		parser = new QueryParser("firstName >= \"C\" AnD lastName !=    \"K\"   aNd jmbag LIKE \"*\" ");
		List<ConditionalExpression> list = parser.getQuery();
		
		assertEquals(3, list.size());
		
		assertEquals(list.get(0).getComparisonOperator(), ComparisonOperators.GREATER_OR_EQUALS);
		assertEquals(list.get(0).getLiteral(), "C");
		assertEquals(list.get(0).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);
		
		assertEquals(list.get(1).getComparisonOperator(), ComparisonOperators.NOT_EQUALS);
		assertEquals(list.get(1).getLiteral(), "K");
		assertEquals(list.get(1).getFieldValueGetter(), FieldValueGetters.LAST_NAME);
		
		assertEquals(list.get(2).getComparisonOperator(), ComparisonOperators.LIKE);
		assertEquals(list.get(2).getLiteral(), "*");
		assertEquals(list.get(2).getFieldValueGetter(), FieldValueGetters.JMBAG);
	}
}
