package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.model.PollOption;

/**
 * Razred <code>PieChartServlet</code> predstavlja servlet koji prikazuje
 * rezultate glasanja pomoću tortnog dijagrama.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-grafika" })
public class PieChartServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png; charset=utf-8");

		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset, "");

		BufferedImage image = chart.createBufferedImage(400, 400);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", bos);
		resp.getOutputStream().write(bos.toByteArray());
	}

	/**
	 * Stvara skup podataka s vrijednostima (naziv atributa, broj glasova).
	 */
	@SuppressWarnings("unchecked")
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();
		List<PollOption> data = (List<PollOption>) req.getServletContext().getAttribute("data");

		for (PollOption po : data) {
			result.setValue(po.getTitle(), Integer.parseInt(po.getNoOfVotes()));
		}
		return result;

	}

	/**
	 * Stvara tortni dijagram i legendu.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}
