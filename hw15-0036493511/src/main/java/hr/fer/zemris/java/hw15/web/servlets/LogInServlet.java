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
 * Razred predstavlja servlet koji ažurira trenutnog korisnika. Prilikom
 * naispravnih unosa javlja greške.
 * 
 * @author Filip
 *
 */
@WebServlet("/servleti/login")
public class LogInServlet extends HttpServlet {

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
	 * Pomoćna metoda, koja provjera točnost unosa te stvara novog
	 * {@link BlogUser}-a ako nema grešaka.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 * @param resp
	 *            {@link HttpServletResponse}
	 * @throws ServletException
	 *             u slučaju greške
	 * @throws IOException
	 *             u slučaju greške
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		FormularL fl = new FormularL();
		fl.getFromHttpRequest(req);
		fl.validate();

		if (fl.haveErrors()) {
			req.setAttribute("zapisL", fl);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			return;
		}

		BlogUser user = DAOProvider.getDAO().getBlogUser(fl.getNickname());
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.nickname", user.getNickname());

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}
