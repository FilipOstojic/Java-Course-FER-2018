package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Razred <code>PowerServlet</code> predstavlja servlet koji na temelju
 * parametara a, b i n stvara novi xml dokument. Dokument sadrži n stranica, a
 * na svakoj stranici u prvom stupcu brojevi od a do b, a u drugom vrijednost
 * broja na potenciju broja stranice. Dopuštene vrijednosti za a i b su:
 * [-100,100], a za n: [1,5]. Ako vrijednost/i fale pojavit će se obvjest o
 * pogrešci.
 * 
 * @author Filip
 *
 */
@WebServlet(urlPatterns = { "/powers" })
public class PowerServlet extends HttpServlet {
	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a;
		Integer b;
		Integer n;
		try {
			a = req.getParameter("a") == "" ? null : Integer.parseInt(req.getParameter("a"));
			b = req.getParameter("b") == "" ? null : Integer.parseInt(req.getParameter("b"));
			n = req.getParameter("n") == "" ? null : Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException e) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (a == null || b == null || n == null) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		resp.setContentType("application/vnd.ms-excel; charset=utf-8");
		resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");

		HSSFWorkbook hwb = new HSSFWorkbook();
		createSheets(hwb, a, b, n);
		OutputStream out = resp.getOutputStream();
		hwb.write(out);
		out.flush();
		out.close();
	}

	/**
	 * Pomoćna metoda. Stvara stranice i puni redove pripadnim vrijednostima.
	 * 
	 * @param hwb
	 *            {@link HSSFWorkbook}
	 * @param a
	 *            početna vrijednost
	 * @param b
	 *            konačna vrijednost
	 * @param n
	 *            broj stranica i potencija
	 */
	private void createSheets(HSSFWorkbook hwb, int a, int b, int n) {
		int delta = Math.abs(a - b);
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("sheet" + i);
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Number");
			rowhead.createCell((short) 1).setCellValue("Number^" + i);
			int valueMin = Math.min(a, b);
			for (int j = 1; j <= delta + 1; j++) {
				HSSFRow row = sheet.createRow((short) j);
				row.createCell((short) 0).setCellValue(String.valueOf(valueMin));
				row.createCell((short) 1).setCellValue(String.valueOf(Math.pow(valueMin, i)));
				valueMin++;
			}
		}
	}

}
