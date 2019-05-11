package hr.fer.zemris.java.gui.calc;

import static org.junit.Assert.assertEquals;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.Test;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Osnovni testovi za ispravan rad {@link CalcLayout}-a.
 * 
 * @author Filip
 *
 */
public class CalcLayoutTest {

	@Test
	public void testFromHW1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(dim.getHeight(), 158, 0);
		assertEquals(dim.getWidth(), 152, 0);

	}

	@Test
	public void testFromHW2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(dim.getHeight(), 158, 0);
		assertEquals(dim.getWidth(), 152, 0);
	}

	@Test(expected = CalcLayoutException.class)
	public void testException1() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), new RCPosition(1, 2));
	}

	@Test(expected = CalcLayoutException.class)
	public void testException2() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), new RCPosition(6, 2));
	}

	@Test(expected = CalcLayoutException.class)
	public void testException3() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), new RCPosition(0, 3));
	}

	@Test(expected = CalcLayoutException.class)
	public void testException4() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), new RCPosition(3, 8));
	}

	@Test(expected = CalcLayoutException.class)
	public void testException5() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), new RCPosition(-1, -1));
	}

	@Test(expected = CalcLayoutException.class)
	public void testException6() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), "0,2");
	}

	@Test(expected = CalcLayoutException.class)
	public void testException7() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), "6,2");
	}

	@Test(expected = CalcLayoutException.class)
	public void testException8() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), "1,4");
	}

}
