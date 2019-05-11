package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Razred koji implementira sučelje <code>DAO</code>. Implementira metode koje
 * dohvaćaju objekte kroz rad sa bazom podataka.
 * 
 * @author Filip
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	public List<BlogEntry> getBlogEntries(Long id) throws DAOException {
		BlogUser user = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return (user != null) ? user.getEntries() : null;
	}

	@Override
	public BlogUser getBlogUser(String nickname) throws DAOException {
		List<BlogUser> users = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.one", BlogUser.class)
				.setParameter("bu", nickname).getResultList();
		return (users == null || users.size() == 0) ? null : users.get(0);
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		List<BlogUser> users = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.all", BlogUser.class)
				.getResultList();
		return (users == null || users.size() == 0) ? null : users;
	}

	@Override
	public void addBlogUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public void removeBlogUsers() throws DAOException {
		List<BlogUser> users = getBlogUsers();
		if (users == null)
			return;
		for (BlogUser user : users) {
			JPAEMProvider.getEntityManager().remove(user);
		}
	}

	@Override
	public List<BlogEntry> getEntries(Long id) throws DAOException {
		BlogUser user = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return (user != null) ? user.getEntries() : null;
	}

	@Override
	public void addNewBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	@Override
	public void updateEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().merge(entry);
	}

	@Override
	public void addNewComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}
}