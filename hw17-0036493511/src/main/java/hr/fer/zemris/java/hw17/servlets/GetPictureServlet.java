package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw17.PictureUtil;

/**
 * Servlet koji dohvaća sliku u orginalnoj veličini. Podatak o orginalnoj slici
 * prima preko parametra zadanog na klik thumbnaila.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/getPicture" })
public class GetPictureServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String filename = req.getParameter("fileName");

		String picsFolder = req.getServletContext().getRealPath("WEB-INF/slike");
		String picturePath = Paths.get(picsFolder).resolve(Paths.get(filename)).toString();

		BufferedImage picture = PictureUtil.loadImage(picturePath);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(picture, "jpg", bos);
		resp.getOutputStream().write(bos.toByteArray());
	}

}
