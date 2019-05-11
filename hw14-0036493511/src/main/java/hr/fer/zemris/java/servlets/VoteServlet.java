package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

/**
 * Servlet koji prosljeđuje dobiveni glas o odabranoj anketi servletu
 * {@link VotingVoteServlet}.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje" })
public class VoteServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");

		Integer pollID = Integer.parseInt(req.getParameter("pollID"));
		DAO dao = DAOProvider.getDao();

		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n");
		sb.append("<body>\n");
		sb.append(String.format("<h1>%s</h1>\n", dao.getPoll(pollID).getTitle()));
		sb.append(String.format("<h2>%s</h2>\n", dao.getPoll(pollID).getMessage()));
		sb.append("<ol>\n");

		List<PollOption> pollOptions = dao.getAllPollOptions();
		for (PollOption po : pollOptions) {
			if (po.getPollID().equals(String.valueOf(pollID))) {
				sb.append("<li><a href=");
				sb.append(String.format("%s/servleti/glasanje-glasaj?id=%s", req.getContextPath(), po.getId()));
				sb.append(String.format(">%s</a></li>", po.getTitle()));
			}
		}

		sb.append("</ol>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");

		resp.getWriter().write(sb.toString());
	}
}
