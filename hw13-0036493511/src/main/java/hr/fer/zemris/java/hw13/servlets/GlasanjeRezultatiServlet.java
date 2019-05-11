package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.servlets.Util.Band;

/**
 * Razred <code>GlasanjeRezultatiServlet</code> predstavlja servlet koji
 * prikazuje razultat glasovanja u tablici kao i ostali sadržaj. Međutim, ako
 * datoteka glasanje-rezultati.txt ne postoji, servlet ju stvara i inicijalizira
 * vrijednosti na nulu.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		if (!Files.exists(Paths.get(fileName))) {
			Util.createResultFile(req);
		}
		List<String> listWithVotes = Util.getList(fileName);

		List<Result> results = getResults(listWithVotes, req);
		List<LinkBand> links = getLinkBand(results);

		req.getSession().setAttribute("results", results);
		req.getSession().setAttribute("links", links);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}

	/**
	 * Vraća listu {@link LinkBand}-a.
	 * 
	 * @param results
	 *            lista {@link Result}-ata
	 * @return lista {@link LinkBand}-a
	 */
	private List<LinkBand> getLinkBand(List<Result> results) {
		List<LinkBand> links = new ArrayList<>();
		LinkBand first = null;

		for (Result result : results) {
			int noOfVotes = Integer.parseInt(result.getNoOfVotes());
			if (noOfVotes != 0) {
				if (first == null) {
					first = new LinkBand(result.song, result.name, noOfVotes);
				} else if (noOfVotes > first.votes) {
					first = new LinkBand(result.song, result.name, noOfVotes);
				} else if (noOfVotes == first.votes) {
					links.add(new LinkBand(result.song, result.name, noOfVotes));
				}
			}
		}
		links.add(first);
		return links;
	}

	/**
	 * Vraća listu {@link Result}-ata.
	 * 
	 * @param list
	 *            lista redova glasovanje-rezultati.txt
	 * @param req
	 *            {@link HttpServletRequest}
	 * @return lista {@link Result}-ata
	 */
	public List<Result> getResults(List<String> list, HttpServletRequest req) {
		List<Band> bands = Util.getBands(req);
		List<String> votes = getVotes(req);

		List<Result> results = new ArrayList<>();
		for (int i = 0; i < bands.size(); i++) {
			results.add(new Result(bands.get(i).name, votes.get(i), bands.get(i).song));
		}
		return results;
	}

	/**
	 * Stvara listu s glasovima bendova.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 * @return lista s glasovima bendova
	 */
	private List<String> getVotes(HttpServletRequest req) {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> list = Util.getList(fileName);

		List<String> votes = new ArrayList<>();
		for (String line : list) {
			votes.add(line.split("\t")[1]);
		}
		return votes;
	}

	/**
	 * Pomoćni razred, čuva vrijednosti: ime benda, broj glasova benda, poveznicu na
	 * pjesmu na YouTube-u.
	 * 
	 * @author Filip
	 *
	 */
	public class Result {
		/**
		 * Ime.
		 */
		String name;
		/**
		 * Broj glasova.
		 */
		String noOfVotes;
		/**
		 * Pjesma.
		 */
		String song;

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            ime
		 * @param noOfVotes
		 *            broj glasova
		 * @param song
		 *            pjesma
		 */
		public Result(String name, String noOfVotes, String song) {
			this.name = name;
			this.noOfVotes = noOfVotes;
			this.song = song;
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
		 * Vraća broj glasova.
		 * 
		 * @return broj glasova
		 */
		public String getNoOfVotes() {
			return noOfVotes;
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

	/**
	 * Pomoćni razred, čuva vrijednosti: ime benda, broj glasova benda, poveznicu na
	 * pjesmu na YouTube-u.
	 * 
	 * @author Filip
	 *
	 */
	public class LinkBand {
		/**
		 * Poveznica.
		 */
		String link;
		/**
		 * Ime benda.
		 */
		String name;
		/**
		 * Broj glasova.
		 */
		int votes;

		/**
		 * Konstruktor.
		 * 
		 * @param link
		 *            poveznica
		 * @param name
		 *            ime benda
		 * @param votes
		 *            broj glasova
		 */
		public LinkBand(String link, String name, int votes) {
			this.link = link;
			this.name = name;
			this.votes = votes;
		}

		/**
		 * Vraća poveznicu.
		 * 
		 * @return poveznica
		 */
		public String getLink() {
			return link;
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
		 * Vraća broj glasova.
		 * 
		 * @return broj glasova
		 */
		public int getVotes() {
			return votes;
		}
	}
}
