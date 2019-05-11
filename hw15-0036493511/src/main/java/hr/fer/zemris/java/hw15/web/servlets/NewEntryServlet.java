package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Servlet služi za stvaranje nove objave na blogu. Objava sadrži naslov i
 * sadržaj.
 * 
 * @author Filip
 *
 */
@WebServlet("/servleti/newEntry")
public class NewEntryServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		createNewEntry(req);
		String nickname = (String) req.getSession().getAttribute("current.user.nickname");
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nickname);
	}

	/**
	 * Pomoćna metoda, stvara novu objavu. Ako je naslov ili sadržaj prazan
	 * postavlja defaultne vrijednosti.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 */
	private void createNewEntry(HttpServletRequest req) {
		String nickname = (String) req.getSession().getAttribute("current.user.nickname");
		DAO dao = DAOProvider.getDAO();

		BlogEntry entry = new BlogEntry();
		if (req.getParameter("title").trim().equals("")) {
			entry.setTitle("Title");
		} else {
			entry.setTitle(req.getParameter("title"));
		}
		if (req.getParameter("text").trim().equals("")) {
			entry.setText("empty");
		} else {
			entry.setText(req.getParameter("text"));
		}

		entry.setCreator(dao.getBlogUser(nickname));
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(new Date());

		dao.addNewBlogEntry(entry);
	}
}