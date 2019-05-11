package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.internationalization.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.internationalization.LJLabel;

/**
 * Metoda stvara status bar koji sadrži trenutni datum i vrijeme (s desne
 * strane), brojač redaka, stupaca i broja označenih slova (u sredini) te ukupnu
 * veličinu datoteke (lijevo).
 * 
 * @return status bar
 */
public class MyStatusBar extends JPanel {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Labela s duljinom datoteke.
	 */
	private JLabel length;
	/**
	 * Labela s brojem linije kursora.
	 */
	private JLabel line;
	/**
	 * Labela s brojem retka kursora.
	 */
	private JLabel column;
	/**
	 * Labela s trenutnim datumom i vremenom.
	 */
	private JLabel dateAndTime;
	/**
	 * Labela s brojem označenih znakova.
	 */
	private JLabel selected;
	/**
	 * Konstanta za postavljanje inicijalnih vrijednosti labela.
	 */
	private static String INITIAL_VALUE = "0";
	/**
	 * Lokalizacijski provider.
	 */
	private FormLocalizationProvider formProvider;

	/**
	 * Konstruktor.
	 * 
	 * @param FormLocalizationProvider
	 *            formProvider
	 */
	public MyStatusBar(FormLocalizationProvider formProvider) {
		this.formProvider = formProvider;
		initComponents();
	}

	/**
	 * Metoda inicijalizira sve labele, stvara sat te radi razmještaj status bara.
	 */
	private void initComponents() {
		setLayout(new GridLayout(1, 3));
		length = new LJLabel(formProvider, "input");
		setLength("0");

		JPanel center = new JPanel();
		line = new JLabel();
		center.add(line);
		column = new JLabel();
		center.add(column);
		selected = new JLabel();
		center.add(selected);
		reset();

		dateAndTime = new JLabel();
		dateAndTime.setHorizontalAlignment(SwingConstants.RIGHT);

		Thread time = new Thread(() -> {
			while (true) {

				Date date = Calendar.getInstance().getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String result = dateFormat.format(date);

				SwingUtilities.invokeLater(() -> dateAndTime.setText(result));

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignorable) {
				}
			}
		});
		time.start();

		add(length);
		add(center);
		add(dateAndTime);
	}

	/**
	 * Ažurira tekst labele length.
	 * 
	 * @param input
	 *            tekst
	 */
	public void setLength(String input) {
		length.setText(formProvider.getString("input") + input);
	}

	/**
	 * Ažurira tekst labele line.
	 * 
	 * @param input
	 *            tekst
	 */
	public void setLine(String input) {
		line.setText("Ln : " + input);
	}

	/**
	 * Ažurira tekst labele column.
	 * 
	 * @param input
	 *            tekst
	 */
	public void setColumn(String input) {
		column.setText("Col : " + input);
	}

	/**
	 * Ažurira tekst labele selected.
	 * 
	 * @param input
	 *            tekst
	 */
	public void setSelected(String input) {
		selected.setText("Sel : " + input);
	}

	/**
	 * Ažurira sve labele na početno stanje.
	 */
	public void reset() {
		setLength(INITIAL_VALUE);
		setLine(INITIAL_VALUE);
		setColumn(INITIAL_VALUE);
		setSelected(INITIAL_VALUE);
	}

}
