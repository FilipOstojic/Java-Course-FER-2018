package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Razred koji predstavlja servlet za obradu podataka novog korisnika koji se
 * želi regitrirati. Ili stvara novog korisnika ili dojavljuje greške.
 * 
 * @author Filip
 *
 */
@WebServlet("/servleti/save")
public class SaveServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Pomoćna metoda koja validira sadržaj i javlja greške ili stvara novog
	 * korisnika.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 * @param resp
	 *            {@link HttpResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		Formular f = new Formular();
		f.getFromHttpRequest(req);
		f.validate();

		if (f.haveErrors()) {
			req.setAttribute("zapis", f);
			req.getRequestDispatcher("/WEB-INF/pages/singUp.jsp").forward(req, resp);
			return;
		}

		BlogUser user = new BlogUser();
		f.generateBlogUser(user);
		DAOProvider.getDAO().addBlogUser(user);
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}
