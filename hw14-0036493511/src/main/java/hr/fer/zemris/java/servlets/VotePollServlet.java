package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Poll;

/**
 * Servlet za odabir jedne od dvije ponuđene ankete: omiljeni bendovi i omiljene
 * igrice.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/index.html" })
public class VotePollServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");

		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n");
		sb.append("<body>\n");
		sb.append("<h2>Select a poll and vote!</h2>\n");
		sb.append("<ol>\n");

		List<Poll> polls = DAOProvider.getDao().getAllPolls();
		for (Poll poll : polls) {
			sb.append("<li><a href=");
			sb.append(String.format(req.getContextPath() + "/servleti/glasanje?pollID=%s", poll.getId()));
			sb.append(String.format(">%s</a></li>", poll.getTitle()));
		}

		sb.append("</ol>\n");
		sb.append("<body>\n");
		sb.append("</html>\n");

		resp.getWriter().write(sb.toString());
	}

}
