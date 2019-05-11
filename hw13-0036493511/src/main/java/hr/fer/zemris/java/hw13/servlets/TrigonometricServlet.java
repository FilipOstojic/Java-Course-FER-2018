package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Razred <code>TrigonometricServlet</code> predstavlja servlet koji na temelju
 * parametara a, b (početna i konačna vrijednost stupnjeva) radi tablicu
 * trigonometrijskih vrijednosti. Tablica sadrži svaki cijeli broj između a i b
 * (uključeno) te njihov sinus i kosinus. Dopuštene vrijednosti parametara:
 * [0,360]. Ako vrijednost/i fale pojavit će se obvjest o pogrešci.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		try {
			a = req.getParameter("a") == null ? 0 : Integer.parseInt(req.getParameter("a"));
			b = req.getParameter("b") == null ? 360 : Integer.parseInt(req.getParameter("b"));
		} catch (NumberFormatException e) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (a == null || b == null) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		} else if (b > a + 720) {
			b = a + 720;
		}

		List<ParamEntry> list = new ArrayList<>();

		for (int i = a; i <= b; i++) {
			list.add(new ParamEntry(i));
		}

		req.getSession().setAttribute("table", list);
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Pomoćni razred koji čuva vrijednosti: dobiveni broj, njegov sinus te kosinus.
	 * 
	 * @author Filip
	 *
	 */
	public static class ParamEntry {
		/**
		 * Broj.
		 */
		String num;
		/**
		 * Kosinus broja.
		 */
		String cos;
		/**
		 * Sinus broja.
		 */
		String sin;

		/**
		 * Konstruktor.
		 * 
		 * @param degrees
		 *            broj u stupnjevima.
		 */
		public ParamEntry(int degrees) {
			num = String.valueOf(degrees);
			cos = String.format("%.5f", Math.cos(Math.toRadians(degrees)));
			sin = String.format("%.5f", Math.sin(Math.toRadians(degrees)));
		}

		/**
		 * Vraća kosinus broja.
		 * 
		 * @return kosinus broja
		 */
		public String getCos() {
			return cos;
		}

		/**
		 * Vraća sinus broja.
		 * 
		 * @return sinus broja
		 */
		public String getSin() {
			return sin;
		}

		/**
		 * Vraća vrijednost broja.
		 * 
		 * @return vrijednost broja
		 */
		public String getNum() {
			return num;
		}
	}

}
