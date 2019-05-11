package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Razred <code>Util</code> sadrži pomoćne metode koje se koriste u servletima.
 * 
 * @author Filip
 *
 */
public class Util {

	/**
	 * Stvara datoteku glasanje-rezultati.txt te u nju zapisuje imena bendova i
	 * inicijalni broj glasova.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 */
	public static void createResultFile(HttpServletRequest req) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("src/main/webapp/WEB-INF/glasanje-rezultati.txt", "UTF-8");
			String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

			List<String> lines = Files.readAllLines(Paths.get(fileName));

			for (String line : lines) {
				String[] chunks = line.split("\t");
				if (chunks[0].trim().equals(req.getParameter("id"))) {
					writer.println(chunks[0] + "\t1");
				} else {
					writer.println(chunks[0] + "\t0");
				}
			}
		} catch (Exception e) {

		} finally {
			writer.close();
		}
	}

	/**
	 * Metoda ažurira datoteku glasanje-rezultati.txt tako što dodaje glas onom
	 * bendu koji je glas dobio.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 */
	public static void vote(HttpServletRequest req) {
		PrintWriter writer = null;
		try {

			String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
			List<String> lines = Files.readAllLines(Paths.get(fileName));
			writer = new PrintWriter("src/main/webapp/WEB-INF/glasanje-rezultati.txt", "UTF-8");

			for (String line : lines) {
				String[] chunks = line.split("\t");
				if (chunks[0].trim().equals(req.getParameter("id"))) {
					writer.println(chunks[0] + "\t" + String.valueOf(Integer.parseInt(chunks[1]) + 1));
				} else {
					writer.println(chunks[0] + "\t" + chunks[1]);
				}
			}
		} catch (IOException e) {

		} finally {
			writer.close();
		}
	}

	public static List<String> getList(String fileName) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (Exception e) {
			return null;
		}
		return lines;
	}

	/**
	 * Metoda parsira datoteku s nazivima definicijama bendova te stvara listu
	 * {@link Band}-ova.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 * @return lista {@link Band}-ova
	 */
	public static List<Band> getBands(HttpServletRequest req) {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> list = getList(fileName);

		List<Band> bands = new ArrayList<>();
		for (String line : list) {
			if (line.equals(""))
				continue;
			String[] chunks = line.split("\t");
			bands.add(new Band(chunks[0].trim(), chunks[1].trim(), chunks[2].trim()));
		}
		return bands;
	}

	/**
	 * Pomoćni razred koji sadrži informacije o bendu: id, naziv i poveznicu na
	 * pjesmu na YouTube-u.
	 * 
	 * @author Filip
	 *
	 */
	public static class Band {
		/**
		 * ID benda.
		 */
		String id;
		/**
		 * Ime benda.
		 */
		String name;
		/**
		 * Poveznicu na pjesmu na YouTube-u tog benda.
		 */
		String song;

		/**
		 * Konstruktor.
		 * 
		 * @param id
		 *            id
		 * @param name
		 *            naziv
		 * @param song
		 *            pjesma
		 */
		public Band(String id, String name, String song) {
			this.id = id;
			this.name = name;
			this.song = song;
		}

		/**
		 * Vraća id.
		 * 
		 * @return id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Vraća ime.
		 * 
		 * @return ime
		 */
		public String getName() {
			return name;
		}

		/**
		 * Vraća pjesmu.
		 * 
		 * @return pjesma
		 */
		public String getSong() {
			return song;
		}
	}
}
