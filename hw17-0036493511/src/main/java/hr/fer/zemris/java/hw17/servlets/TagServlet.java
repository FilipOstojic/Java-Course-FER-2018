package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.PictureUtil;

/**
 * Servlet koji se inicijalno odmah zove, kada se stranica otvori. Dohvaća
 * tagove te ih šalje kao JSON podatke pomoću {@link Gson}-a.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/tags" })
public class TagServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=UTF-8");
		PictureUtil.contextPath = req.getServletContext().getRealPath("/WEB-INF/");

		Set<String> allTags = PictureUtil.getTags();
		String[] array = new String[allTags.size()];
		allTags.toArray(array);

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
