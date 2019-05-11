package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Predstavlja servlet koji obrađuje url i ovisno o njegovom sadržaju
 * presljeđuje daljnju obradu.
 * 
 * @author Filip
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		String[] chunks = pathInfo.split("/");
		String providedNickname = chunks[1];

		Long providedID = DAOProvider.getDAO().getBlogUser(providedNickname).getId();
		req.getSession().setAttribute("provided.user.nick", providedNickname);
		req.setAttribute("entries", DAOProvider.getDAO().getBlogEntries(providedID));

		String currentNickname = (String) req.getSession().getAttribute("current.user.nickname");

		if (currentNickname == null || !currentNickname.equals(providedNickname)) {
			req.getSession().setAttribute("providedEqual", false);
		} else {
			req.getSession().setAttribute("providedEqual", true);
		}

		if (chunks.length == 2) {
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
			return;
		}

		switch (chunks[2]) {
		case "new":
			if (currentNickname == null || !currentNickname.equals(providedNickname)) {
				req.setAttribute("message", "Current and provided ID do not match.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			req.getRequestDispatcher("/WEB-INF/pages/newBlogEntry.jsp").forward(req, resp);
			break;
		case "edit":
			if (currentNickname == null || !currentNickname.equals(providedNickname)) {
				req.setAttribute("message", "Current and provided ID do not match.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}

			req.getRequestDispatcher("/WEB-INF/pages/editBlogEntry.jsp").forward(req, resp);
			break;
		default:
			Long id = null;
			try {
				id = Long.valueOf(chunks[2]);
			} catch (Exception ex) {
				req.setAttribute("message", "Incorrect parameters.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			create(req, id, providedNickname);

			req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
			break;
		}
	}

	/**
	 * Pomoćna metoda, stvara objavu.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 * @param id
	 *            id
	 * @param providedNickname
	 *            dobiveni nadimak
	 */
	private void create(HttpServletRequest req, Long id, String providedNickname) {
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

		if (entry == null || !providedNickname.equals(entry.getCreator().getNickname())) {
			throw new RuntimeException();
		}

		req.getSession().setAttribute("current.entry", entry);
	}

}
