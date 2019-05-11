package hr.fer.zemris.java.dao.sql;

import java.sql.Connection;
import static hr.fer.zemris.java.util.SqlCommands.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * završetku obrade je maknuti.
 * 
 * @author Filip
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getAllPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(GET_ALL_POLLS);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(String.valueOf(rs.getLong(1)));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Exception occured while retrieving the data.", ex);
		}
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(GET_POLL);
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						poll = new Poll();
						poll.setId(String.valueOf(rs.getLong(1)));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Exception occured while retrieving the data.", ex);
		}
		return poll;
	}

	@Override
	public List<PollOption> getAllPollOptions() throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(GET_ALL_OPTION_POLLES);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption po = new PollOption();
						po.setId(String.valueOf(rs.getLong(1)));
						po.setTitle(rs.getString(2));
						po.setLink(rs.getString(3));
						po.setPollID(rs.getString(4));
						po.setNoOfVotes(rs.getString(5));
						pollOptions.add(po);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Exception occured while retrieving the data.", ex);
		}
		return pollOptions;
	}

	@Override
	public PollOption getPollOption(long id) throws DAOException {
		PollOption po = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(GET_OPTION_POLL);
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						po = new PollOption();
						po.setId(String.valueOf(rs.getLong(1)));
						po.setTitle(rs.getString(2));
						po.setLink(rs.getString(3));
						po.setPollID(rs.getString(4));
						po.setNoOfVotes(rs.getString(5));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Exception occured while retrieving the data.", ex);
		}
		return po;
	}

	@Override
	public void incrementVote(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(INCREMENT_VOTE);
			pst.setLong(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Exception occured while retrieving the data.", e);
		} finally {
			try {
				pst.close();
			} catch (Exception ignorable) {
			}
		}

	}

}