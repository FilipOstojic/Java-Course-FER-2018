package hr.fer.zemris.java.servlets;

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

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

/**
 * Razred <code>XLSServlet</code> predstavlja servlet koji na temelju atributa
 * ({@link PollOption}) i njihovih glasova kreira xls dokument s rezultatima
 * glasovanja. U prvom su stupcu atributi, a u drugom brojevi glasova.
 * 
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-xls" })
public class XLSServlet extends HttpServlet{

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
		int pollID = (int)req.getServletContext().getAttribute("pollID");
		
		String [] chunks = DAOProvider.getDao().getPoll(pollID).getTitle().split("\\s+");
		String title = chunks[chunks.length-1];
		
		rowhead.createCell((short) 0).setCellValue(title);
		rowhead.createCell((short) 1).setCellValue("number of votes");
		
		int counter = 0;
		List<PollOption> data = (List<PollOption>) req.getServletContext().getAttribute("data");
		for (PollOption po : data) {
			HSSFRow row = sheet.createRow((short) ++counter);
			row.createCell((short) 0).setCellValue(po.getTitle());
			row.createCell((short) 1).setCellValue(po.getNoOfVotes());
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
