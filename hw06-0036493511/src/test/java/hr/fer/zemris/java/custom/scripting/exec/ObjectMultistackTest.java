package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testovi za razred <code>ObjectMultistackTest</code>. Testovi testiraju sve
 * metode razreda <code>ObjectMultistackTest</code>.
 * 
 * @author Filip
 *
 */
public class ObjectMultistackTest {

	ObjectMultistack multiStack;

	public ObjectMultistack initialize() {
		multiStack = new ObjectMultistack();
		multiStack.push("Franjo", new ValueWrapper(3));
		multiStack.push("Marko", new ValueWrapper(5));
		multiStack.push("Carmen", new ValueWrapper(4));
		multiStack.push("Franjo", new ValueWrapper("4"));
		multiStack.push("Franjo", new ValueWrapper(5.0));
		return multiStack;
	}

	@Test
	public void testPushPopPeek() {
		ObjectMultistack stack = initialize();

		assertEquals(5.0, stack.peek("Franjo").getValue());
		assertEquals(5, stack.peek("Marko").getValue());
		assertEquals(4, stack.peek("Carmen").getValue());

		assertEquals(5.0, stack.pop("Franjo").getValue());
		assertEquals("4", stack.peek("Franjo").getValue());
		assertEquals("4", stack.pop("Franjo").getValue());
		assertEquals(3, stack.peek("Franjo").getValue());
		assertEquals(3, stack.pop("Franjo").getValue());

		assertTrue(stack.isEmpty("Franjo"));
		assertFalse(stack.isEmpty("Carmen"));
	}
	
	@Test
	public void testPushPopPeek2() {
		ObjectMultistack stack = initialize();

		stack.push("Ana", new ValueWrapper(null));
		stack.push("Ana", new ValueWrapper(0));
		stack.push("Ana", new ValueWrapper("Mara"));
		
		assertFalse(stack.isEmpty("Ana"));
		stack.pop("Ana");
		assertEquals(0, stack.peek("Ana").getValue());
		stack.push("Ana", new ValueWrapper(0));
		assertEquals(0, stack.peek("Ana").getValue());
		assertEquals(0, stack.pop("Ana").getValue());
		assertEquals(0, stack.pop("Ana").getValue());
		assertEquals(null, stack.pop("Ana").getValue());
		assertTrue(stack.isEmpty("Ana"));
	}

	@Test
	public void testPop() {
		ObjectMultistack stack = initialize();

		assertEquals(5, stack.pop("Marko").getValue());
		assertEquals(4, stack.pop("Carmen").getValue());
	}

	@Test(expected = EmptyStackException.class)
	public void testPopWrongKey() {
		ObjectMultistack stack = initialize();

		stack.pop("Anastazia");
	}

	@Test(expected = NullPointerException.class)
	public void testPopNull() {
		ObjectMultistack stack = initialize();

		stack.pop(null);
	}

	@Test
	public void testPush() {
		ObjectMultistack stack = initialize();

		stack.push("Janica", new ValueWrapper(2.0));
		stack.push("Anita", new ValueWrapper("baklava"));
	}

	@Test(expected = NullPointerException.class)
	public void testPushNullKey() {
		ObjectMultistack stack = initialize();

		stack.push(null, new ValueWrapper(4));
	}

	@Test
	public void testPushNullValue() {
		ObjectMultistack stack = initialize();

		stack.push("Janica", new ValueWrapper(null));
		stack.push("Ivica", new ValueWrapper(null));
	}

	@Test
	public void testPushAndPop() {
		ObjectMultistack stack = initialize();

		assertTrue(stack.isEmpty("Josip"));
		assertFalse(stack.isEmpty("Carmen"));

		stack.push("Josip", new ValueWrapper(stack.pop("Carmen")));

		assertFalse(stack.isEmpty("Josip"));
		assertTrue(stack.isEmpty("Carmen"));
	}

	@Test(expected = EmptyStackException.class)
	public void testPeekingEmptyStack() {
		ObjectMultistack stack = new ObjectMultistack();

		stack.peek("Filip");
	}
}
