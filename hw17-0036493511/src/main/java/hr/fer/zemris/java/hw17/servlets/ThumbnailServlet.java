package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
 * Servlet koji dohvaća traženi thumbnail iz datoteke. Ako takav ne postoji
 * učitava orginalnu sliku te stvara thumbnail i sprema ga.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/getThumb" })
public class ThumbnailServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PictureUtil.contextPath = req.getServletContext().getRealPath("/WEB-INF/");
		String filename = req.getParameter("fileName");

		String thumbsFolder = req.getServletContext().getRealPath("/WEB-INF/thumbnails");
		String picsFolder = req.getServletContext().getRealPath("/WEB-INF/slike");
		String picturePath = Paths.get(picsFolder, filename).toString();
		String thumbPath = Paths.get(thumbsFolder, filename).toString();

		PictureUtil.createThumbsFolder(thumbsFolder);
		BufferedImage thumb = PictureUtil.loadImage(thumbPath);

		if (thumb == null) {
			BufferedImage bigImage = PictureUtil.loadImage(picturePath);
			thumb = PictureUtil.resize(bigImage, 150, 150);
			File output = new File(thumbPath);
			ImageIO.write(thumb, "jpg", output);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(thumb, "jpg", bos);
		resp.getOutputStream().write(bos.toByteArray());
	}

}
