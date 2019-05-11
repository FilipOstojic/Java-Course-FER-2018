package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Sučelje prema podsustavu za perzistenciju podataka (komunikacija s bazom
 * podataka).
 * 
 * @author Filip
 *
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Dohvaća listu entry-a sa zadanim <code>id</code>-em. Ako takav entry ne
	 * postoji, vraća <code>null</code>.
	 * 
	 * @param id
	 *            id usera
	 * @return lista entry-a
	 * @throws DAOException
	 */
	List<BlogEntry> getBlogEntries(Long id) throws DAOException;

	/**
	 * Dohvaća user-a sa zadanim <code>nickname</code>-om. Ako takav entry ne
	 * postoji, vraća <code>null</code>.
	 * 
	 * @param nickname
	 *            nadimak user-a
	 * @return user
	 * @throws DAOException
	 */
	BlogUser getBlogUser(String nickname) throws DAOException;

	/**
	 * Dohvaća listu user-a. Ako takava entry ne postoji, vraća <code>null</code>.
	 * 
	 * @return lista user-a
	 * @throws DAOException
	 */
	List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Dodaje {@link BlogUser} u bazu podataka.
	 * 
	 * @param user
	 *            {@link BlogUser}
	 * @throws DAOException
	 */
	void addBlogUser(BlogUser user) throws DAOException;

	/**
	 * Uklanja se {@link BlogUser} iz baze podataka.
	 * 
	 * @throws DAOException
	 */
	void removeBlogUsers() throws DAOException;

	/**
	 * Ažurira {@link BlogEntry} u bazi podataka.
	 * 
	 * @param entry
	 *            {@link BlogEntry}
	 * @throws DAOException
	 */
	void updateEntry(BlogEntry entry) throws DAOException;

	/**
	 * Dodaje novi {@link BlogComment} na {@link BlogEntry}.
	 * 
	 * @param comment
	 *            {@link BlogComment}
	 * @throws DAOException
	 */
	void addNewComment(BlogComment comment) throws DAOException;

	/**
	 * Dohvaća listu {@link BlogEntry} za zadani userID.
	 * 
	 * @param id
	 *            userID
	 * @return lista {@link BlogEntryn
	 * @throws DAOException
	 */
	List<BlogEntry> getEntries(Long id) throws DAOException;

	/**
	 * Dodaje novi {@link BlogEntry}.
	 * 
	 * @param entry
	 *            {@link BlogEntry}
	 * @throws DAOException
	 */
	void addNewBlogEntry(BlogEntry entry) throws DAOException;

}