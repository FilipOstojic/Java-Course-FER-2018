package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Glavni program. Kroz komandnu liniju prima jedan argument - put do datoteke s
 * podacima za dijagram. Stvara korisničko sučelje.
 * 
 * @author Filip
 *
 */
public class BarChartDemo extends JFrame {
	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Dijagram.
	 */
	private BarChart chart;
	/**
	 * Putanja do datoteke.
	 */
	private Path path;

	/**
	 * Konstruktor.
	 * 
	 * @param chart
	 *            dijagram
	 * @param path
	 *            putanja do datoteke
	 */
	public BarChartDemo(BarChart chart, Path path) {
		this.chart = chart;
		this.path = path;

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500,500));
		setVisible(true);
		setTitle("BarChart Demo");

		initGUI();
	}

	/**
	 * Metoda inicijalizira grafičko korisničko sučelje.
	 */
	private void initGUI() {
		getContentPane().setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		add(new BarChartComponent(chart), BorderLayout.CENTER);
		JLabel label = new JLabel(path.toAbsolutePath().toString());
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label, BorderLayout.PAGE_START);
	}

	/**
	 * Metoda se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected one argument, got: " + args.length);
			return;
		}

		try {
			List<String> lines = getLines(Paths.get(args[0]));
			SwingUtilities.invokeLater(() -> {
				new BarChartDemo(createBarChart(lines), Paths.get(args[0]));
			});
		} catch (Exception e) {
			System.out.println("Error occured while reading from file!");
			System.exit(1);
		}
	}

	/**
	 * Metoda stvara dijagram.
	 * 
	 * @param lines
	 *            lista podataka iz ulazne datoteke
	 * @return dijagram
	 */
	private static BarChart createBarChart(List<String> lines) {
		String xDescr = lines.get(0).trim();
		String yDescr = lines.get(1).trim();
		List<XYValue> list = getVYValues(lines.get(2).trim());
		int min = Integer.parseInt(lines.get(3).trim());
		int max = Integer.parseInt(lines.get(4).trim());
		int delta = Integer.parseInt(lines.get(5).trim());

		return new BarChart(list, xDescr, yDescr, min, max, delta);
	}

	/**
	 * Metoda parsira liniju s vrijednostima i stvara primjerke razreda
	 * {@link XYValue}.
	 * 
	 * @param line
	 *            redak
	 * @returnlista {@link XYValue}-a
	 */
	private static List<XYValue> getVYValues(String line) {
		List<XYValue> list = new ArrayList<>();
		String[] array = line.split("\\s+");
		for (int i = 0; i < array.length; i++) {
			String[] value = array[i].split(",");
			list.add(new XYValue(Integer.parseInt(value[0]), Integer.parseInt(value[1])));
		}
		return list;
	}

	/**
	 * Metoda čita datoteku i vraća listu redaka.
	 * 
	 * @param path
	 *            putanja do datoteke
	 * @return lista redaka
	 * @throws IOException
	 */
	private static List<String> getLines(Path path) throws IOException {
		return Files.readAllLines(path);
	}

}
