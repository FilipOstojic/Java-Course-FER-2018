package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.Picture;
import hr.fer.zemris.java.hw17.PictureUtil;

/**
 * Servlet koji vraća sve slike koji koje sadrže zadani tag. Tag je zadan kao
 * parametar dobiven klikom na neki od ponuđenih tagova na web stranici.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/picture" })
public class PictureServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PictureUtil.contextPath = req.getServletContext().getRealPath("/WEB-INF/");
		String tag = req.getParameter("tag");

		List<Picture> pics = PictureUtil.getFilteredPictures(tag);
		Picture[] array = new Picture[pics.size()];
		pics.toArray(array);

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
