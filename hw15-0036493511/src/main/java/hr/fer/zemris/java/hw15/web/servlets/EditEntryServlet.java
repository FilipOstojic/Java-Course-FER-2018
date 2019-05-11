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
 * Razred predstavlja servlet koji ažurira objavu na blogu.
 * 
 * @author Filip
 *
 */
@WebServlet("/servleti/editEntry")
public class EditEntryServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		editEntry(req);
		String nickname = (String) req.getSession().getAttribute("current.user.nickname");
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nickname);
	}

	/**
	 * Pomoćna metoda, ažurira objavu.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 */
	private void editEntry(HttpServletRequest req) {
		Long id = Long.valueOf(req.getParameter("entryID"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

		entry.setTitle(req.getParameter("title"));
		entry.setText(req.getParameter("text"));

		DAOProvider.getDAO().updateEntry(entry);
	}
}
