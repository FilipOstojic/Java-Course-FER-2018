package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Razred <code>GlasanjeGlasajServlet</code> predstavlja servlet koji pribraja
 * glasove bendovima. Ako datoteka glasanje-rezultati.txt ne postoji, servlet ju
 * stvara i inicijalizira vrijednosti (pribraja odmah i inicijalni glas). U
 * suprotnom povećava broj glasova bendu za kojeg se glasalo.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(fileName);

		if (!Files.exists(path)) {
			Util.createResultFile(req);
		} else {
			Util.vote(req);
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
