package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.*;
import static hr.fer.zemris.java.hw07.crypto.Util.*;

import org.junit.Test;

/**
 * Razred <code>UtilTest</code> sadrži osnovne testove za metode razreda
 * <code>Util</code>: bytetohex i hextobyte.
 * 
 * @author Filip
 *
 */
public class UtilTest {

	@Test
	public void test1() {
		assertEquals("124aef2231", bytetohex(hextobyte("124aef2231")));
	}

	@Test
	public void test2() {
		assertEquals("afe738b9", bytetohex(hextobyte("AFe738b9")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void test3() {
		assertEquals("af7389", bytetohex(hextobyte("AFeN73G8b9")));

	}

	@Test
	public void test4() {
		assertEquals(0, bytetohex(hextobyte(" ")).length());
	}
}
