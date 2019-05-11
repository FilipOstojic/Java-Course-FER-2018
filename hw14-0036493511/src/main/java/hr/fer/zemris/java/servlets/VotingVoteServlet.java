package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOProvider;

/**
 * Servlet koji na temelju dobivenog id-a inkrementira broj glasova. Poziva
 * servlet za prikaz rezultata {@link VotingResultsServlet}.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-glasaj" })
public class VotingVoteServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		int id = Integer.parseInt(req.getParameter("id"));

		DAO dao = DAOProvider.getDao();
		dao.incrementVote(id);

		int pollID = Integer.parseInt(dao.getPollOption(id).getPollID());

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}

}
