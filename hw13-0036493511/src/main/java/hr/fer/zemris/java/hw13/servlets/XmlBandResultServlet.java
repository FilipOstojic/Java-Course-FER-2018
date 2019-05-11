package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.servlets.GlasanjeRezultatiServlet.Result;

/**
 * Razred <code>XmlBandResultServlet</code> predstavlja servlet koji na temelju
 * bendova i njihovih glasova xml dokument s dva stupca: Ime benda i broj
 * glasova, te slijede svi bendovi i njihovi brojevi glasova.
 * 
 * 
 * @author Filip
 *
 */
@WebServlet("/glasanje-xls")
public class XmlBandResultServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

		HSSFWorkbook hwb = new HSSFWorkbook();
		createSheet(hwb, req, resp);
	}

	@SuppressWarnings("unchecked")
	private void createSheet(HSSFWorkbook hwb, HttpServletRequest req, HttpServletResponse resp) {
		HSSFSheet sheet = hwb.createSheet("sheet 1");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Bend");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");
		List<Result> data = (List<Result>) req.getSession().getAttribute("results");
		if (data == null || data.size() == 0)
			return;
		for (int j = 1; j <= data.size(); j++) {
			HSSFRow row = sheet.createRow((short) j);
			row.createCell((short) 0).setCellValue(data.get(j - 1).name);
			row.createCell((short) 1).setCellValue(data.get(j - 1).noOfVotes);
		}

		writeAndClose(hwb, resp);
	}

	/**
	 * Metoda stvara xml datoteku te zatvara output sream.
	 * 
	 * @param hwb
	 *            {@link HSSFWorkbook}
	 * @param resp
	 *            {@link HttpServletResponse}
	 */
	private void writeAndClose(HSSFWorkbook hwb, HttpServletResponse resp) {
		try {
			resp.setContentType("application/vnd.ms-excel; charset=utf-8");
			resp.setHeader("Content-Disposition", "attachment; filename=\"VotingResults.xls\"");

			OutputStream out = resp.getOutputStream();
			hwb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	}

}
