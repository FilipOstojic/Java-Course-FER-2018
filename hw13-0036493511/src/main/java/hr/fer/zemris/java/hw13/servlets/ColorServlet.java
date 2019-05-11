package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Razred <code>ColorServlet</code> predstavlja servlet za odabir boje pozadine
 * koju će web stranica koristiti. Ako se niti jedna boja ne odabere, zadana
 * boja je bijela.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = "/setColor")
public class ColorServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		// if (color != null) {
		req.getSession().setAttribute("pickedBgCol", color);
		// }
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
