package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred <code>PrimDemo</code> stavara grafičko korisničko sučelje s dvije
 * liste i gumbom za dodavanje sljedećeg prostog broja.
 * 
 * @author Filip
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * Serijaska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Pretpostavljena vrijednost x koordinate.
	 */
	private static int DEFAULT_X = 100;
	/**
	 * Pretpostavljena vrijednost y koordinate.
	 */
	private static int DEFAULT_Y = 100;
	/**
	 * Pretpostavljena širina.
	 */
	private static int DEFAULT_WIDTH = 300;
	/**
	 * Pretpostavljena visina.
	 */
	private static int DEFAULT_HEIGHT = 500;

	/**
	 * Konstruktor.
	 */
	public PrimDemo() {
		setBounds(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Generator prostih brojeva PrimDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Metoda stvara grafičko korisničko sučelje.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel topPanel = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(1, 0));

		topPanel.add(new JScrollPane(list1));
		topPanel.add(new JScrollPane(list2));

		JButton sljedeciButton = new JButton("sljedeci");
		buttonPanel.add(sljedeciButton);
		sljedeciButton.addActionListener(e -> {
			model.next();
		});

		cp.add(topPanel, BorderLayout.CENTER);
		cp.add(buttonPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Metoda koja se poziva kada se program pokrene.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
