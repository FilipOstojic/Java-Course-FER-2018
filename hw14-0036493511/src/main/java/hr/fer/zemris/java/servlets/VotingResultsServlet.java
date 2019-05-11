package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

/**
 * Servlet, prikazuje stranicu s rezutatima. Na njej se nalazi sortitana tablica
 * po broju glasova, dijagram rezultata, link na skidanje xls datotekte s
 * rezultatima te linkovi vodećih atributa ankete.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-rezultati" })
public class VotingResultsServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pollID = Integer.parseInt(req.getParameter("pollID")); 
		StringBuilder sb = new StringBuilder();

		createBegining(sb);
		createTableWithresults(sb, req, pollID);
		createChartView(sb, req, pollID);
		createXLSView(sb, req, pollID);
		createOther(sb, req, pollID);
		createBackButton(sb, req);
		createEnding(sb);

		resp.getWriter().write(sb.toString());
	}

	
	/**
	 * Dodaje link za povratak na izbor anketa.
	 * 
	 * @param sb
	 *            {@link StringBuilder}
	 * @param req
	 *            {@link HttpServletRequest}
	 */
	private void createBackButton(StringBuilder sb, HttpServletRequest req) {
		sb.append("<p><a href=\"/voting-app/index.html\">Return to polls</a></p>");
	}

	/**
	 * Dodaje završetak html dokumenta.
	 * 
	 * @param sb
	 *            {@link StringBuilder}
	 * 
	 */
	private void createEnding(StringBuilder sb) {
		sb.append("</body>\n");
		sb.append("</html>\n");
	}

	/**
	 * Dodaje početak html dokumenta.
	 * 
	 * @param sb
	 *            {@link StringBuilder}
	 * 
	 */
	private void createBegining(StringBuilder sb) {
		sb.append("<html>\n");
		sb.append("<body>\n");
	}

	/**
	 * Dodaje rubriku "Razno". Ispisuje linkove pobjedničkih atributa.
	 * 
	 * @param sb
	 *            {@link StringBuilder}
	 * @param req
	 *            {@link HttpServletRequest}
	 * @param pollID
	 *            id ankete kojoj pripada
	 */
	private void createOther(StringBuilder sb, HttpServletRequest req, int pollID) {
		sb.append("<h2>Other</h2>");
		sb.append("<p>Current leaders:</p>");

		int max = DAOProvider.getDao().getAllPollOptions()
				.stream()
				.filter(po -> Integer.parseInt(po.getPollID()) == pollID)
				.map(po -> Integer.parseInt(po.getNoOfVotes()))
				.collect(Collectors.summarizingInt(Integer::intValue)).getMax();

		List<PollOption> winners = DAOProvider.getDao().getAllPollOptions()
				.stream()
				.filter(po -> Integer.parseInt(po.getPollID()) == pollID)
				.filter(po -> Integer.parseInt(po.getNoOfVotes()) == max)
				.collect(Collectors.toList());

		sb.append("<ul>");
		for (PollOption po : winners) {
			sb.append(String.format("<li><a href=%s target=\"_blank\">%s</a></li>", po.getLink(), po.getTitle()));
		}
		sb.append("</ul>");
	}

	/**
	 * Dodaje rubriku za stvaranje xls dokumenta s rezultatima.
	 * 
	 * @param sb
	 *            {@link StringBuilder}
	 * @param req
	 *            {@link HttpServletRequest}
	 * @param pollID
	 *            id ankete kojoj pripada
	 */
	private void createXLSView(StringBuilder sb, HttpServletRequest req, int pollID) {
		sb.append("<h2>Results in XLS format</h2>");
		sb.append(String.format(
				"<p>Results in xls format are available <a href=\"%s/servleti/glasanje-xls\">here</a></p>",
				req.getContextPath()));
	}

	/**
	 * Dodaje rubriku za prikaz tortnog dijagrama.
	 * 
	 * @param sb
	 *            {@link StringBuilder}
	 * @param req
	 *            {@link HttpServletRequest}
	 * @param pollID
	 *            id ankete kojoj pripada
	 */
	private void createChartView(StringBuilder sb, HttpServletRequest req, int pollID) {
		sb.append("<h2>Graphical view of results</h2>");
		sb.append(String.format(
				"<img alt=\"Pie-chart\" src=\"%s/servleti/glasanje-grafika\" width=\"400\" height=\"400\" />",
				req.getContextPath()));
	}

	/**
	 * Stvara sortiranu tablicu s rezultatima glasovanja.
	 * 
	 * @param sb
	 *            {@link StringBuilder}
	 * @param req
	 *            {@link HttpServletRequest}
	 * @param pollID
	 *            id ankete kojoj pripada
	 */
	private void createTableWithresults(StringBuilder sb, HttpServletRequest req, int pollID) {
		List<PollOption> pollOptions = DAOProvider.getDao().getAllPollOptions();
		pollOptions = pollOptions.stream().sorted((o1, o2) -> o2.getNoOfVotes().compareTo(o1.getNoOfVotes()))
				.collect(Collectors.toList());

		sb.append("<h1>Voting results</h1>");
		sb.append("<p>These are voting results:</p>");
		sb.append("<table border=\"1\" cellspacing=\"0\" class=\"rez\">");
		String[] chunks = DAOProvider.getDao().getPoll(pollID).getTitle().split("\\s+");
		String title = chunks[chunks.length - 1];
		sb.append(String.format("<thead><tr><th>%s</th><th>%s</th></tr></thead>", title, "number of votes"));
		sb.append("<tbody>");

		for (PollOption po : pollOptions) {
			if (po.getPollID().equals(String.valueOf(pollID))) {
				sb.append("<tr>");
				sb.append(String.format("<td>%s</td><td>%s</td>", po.getTitle(), po.getNoOfVotes()));
				sb.append("</tr>");
			}
		}
		sb.append("</tbody></table>");

		pollOptions = pollOptions.stream().filter(op -> Integer.parseInt(op.getPollID()) == pollID)
				.collect(Collectors.toList());
		req.getServletContext().setAttribute("data", pollOptions);
	}

}
