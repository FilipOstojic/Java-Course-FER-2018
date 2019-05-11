package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Servlet služi za stvaranje novog komentara na objavi na forumu.
 * 
 * @author Filip
 *
 */
@WebServlet("/servleti/addComment")
public class NewBlogCommentServlet extends HttpServlet {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BlogEntry entry = addNewComment(req);
		String cn = entry.getCreator().getNickname();
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + cn + "/" + entry.getId());
	}

	/**
	 * Pomoćna metoda, stvara i dodaje novi {@link BlogComment}.
	 * 
	 * @param req
	 *            {@link HttpServletRequest}
	 * @return {@link BlogEntry}
	 */
	private BlogEntry addNewComment(HttpServletRequest req) {
		Long entryID = Long.valueOf(req.getParameter("entryID"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
		DAO dao = DAOProvider.getDAO();
		Date date = new Date();

		String ccn = (String) req.getSession().getAttribute("current.user.nickname");
		BlogUser creator = dao.getBlogUser(ccn);

		BlogComment newComment = new BlogComment();
		newComment.setBlogEntry(entry);
		
		newComment.setMessage(req.getParameter("message"));
		newComment.setPostedOn(date);
		if (creator != null) {
			newComment.setUsersEMail(creator.getEmail());
		} else {
			newComment.setUsersEMail("anonymous");
		}
		dao.addNewComment(newComment);

		return entry;
	}
}
