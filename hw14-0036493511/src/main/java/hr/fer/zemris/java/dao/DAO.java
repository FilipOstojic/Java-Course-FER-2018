package hr.fer.zemris.java.dao;

import java.util.List;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Filip
 *
 */
public interface DAO {

	/**
	 * Dohvaća sve postojeće ankete u bazi.
	 * 
	 * @return listu anketa
	 * @throws DAOException
	 *             u slučaju pogreške
	 */
	public List<Poll> getAllPolls() throws DAOException;

	/**
	 * Dohvaća anketu za zadani id. Ako unos ne postoji, vraća <code>null</code>.
	 * 
	 * @param id
	 *            id od ankete
	 * @return anketa
	 * @throws DAOException
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * Dohvaća sve sadržaje anketa iz baze.
	 * 
	 * @return listu sadržaja svih anketa
	 * @throws DAOException
	 */
	public List<PollOption> getAllPollOptions() throws DAOException;

	/**
	 * Dohvaća određeni sadržaj ankete za zadani id. Ako unos ne postoji, vraća
	 * <code>null</code>.
	 * 
	 * @param id
	 *            id sadržaja ankete
	 * @return sadržaj ankete za zadani id
	 * @throws DAOException
	 */
	public PollOption getPollOption(long id) throws DAOException;

	/**
	 * Povećava broj glasova za 1 sadržaju ankete odgovarajućeg id-a.
	 * 
	 * @param id
	 *            id sadržaja ankete
	 * @throws DAOException
	 */
	public void incrementVote(long id) throws DAOException;

}