package hr.fer.zemris.java;

import static hr.fer.zemris.java.util.SqlCommands.*;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * Razred inicijalizira sadržaj naše baze podataka. Stvara dvije tablice: Polls
 * (id ankete, naslov ankete, poruka ankete) i PollOptions (id sadržaja ankete,
 * naslov sadržaja ankete, link na sadržaj ankete, id od ankete kojoj pripada,
 * broj glasova). Anketu predstavlja razred {@link Poll}, a sadržaj ankete
 * razred {@link PollOption}.
 * 
 * @author Filip
 *
 */
@WebListener
public class Initialization implements ServletContextListener {
	/**
	 * Konstanta za ime atributa u servletovom kontekstu.
	 */
	private final static String DPOOL = "hr.fer.zemris.dbpool";

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException pve) {
			throw new RuntimeException("Exception occured while initializing pool.", pve);
		}
		
		initPool(cpds, sce);
		
		Connection con = null;
		try {
			con = cpds.getConnection();
			checkIfTablesExist(getTableNames(cpds, con), con);
		} catch (SQLException e) {
			throw new RuntimeException("Exception occured while getting connection.", e);
		} finally {
			closeConnection(con);
		}

		sce.getServletContext().setAttribute(DPOOL, cpds);
	}

	/**
	 * Provjerava postoje li tablice Polls i PollOptions i jesu li napunjene. Ako ne
	 * postoje stvara ih i/ili ako nisu napunjene puni ih.
	 * 
	 * @param tableNames
	 *            lista imena postojećih tablica
	 * @param con
	 *            {@link Connection}
	 * @throws SQLException
	 */
	private void checkIfTablesExist(List<String> tableNames, Connection con) throws SQLException {
		Objects.requireNonNull(tableNames);
		String[] pollNames = { "POLLS", "POLLOPTIONS" };
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		for (String name : pollNames) {
			if (!tableNames.contains(name)) {
				pst = con.prepareStatement(name.equals("POLLS") ? CREATE_POLL : CREATE_POLL_OPTIONS);
				pst.executeUpdate();
			}
		}
		for (String name : pollNames) {
			pst = name.equals("POLLS") ? con.prepareStatement(POLLS_SIZE) : con.prepareStatement(POLL_OPTIONS_SIZE);
			rs = pst.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				if (name.equals("POLLS"))
					fillPolls(con);
				else
					fillPollOptions(con, null, null);
			}
		}

		closeStatement(pst);
	}

	/**
	 * Puni tablicu OptionPolls sa svim ponuđenim bendovima i igricama.
	 * 
	 * @param con
	 *            {@link Connection}
	 * @param pollID1
	 *            id bendova
	 * @param pollID2
	 *            id igrica
	 * @throws SQLException
	 */
	private void fillPollOptions(Connection con, Long pollID1, Long pollID2) throws SQLException {
		PreparedStatement pst = null;
		for (String insertBand : FILL_BANDS) {
			pst = con.prepareStatement(insertBand, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, pollID1 == null ? 1 : pollID1);
			pst.executeUpdate();
		}
		for (String insertGame : FILL_GAMES) {
			pst = con.prepareStatement(insertGame, Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, pollID2 == null ? 2 : pollID2);
			pst.executeUpdate();
		}
	}

	/**
	 * Inicijalizira tablicu Poll s anketama za glasanje. Anketa za biranje
	 * omiljenog benda te za biranje omiljne igrice.
	 * 
	 * @param con
	 *            {@link Connection}
	 * @throws SQLException
	 */
	private void fillPolls(Connection con) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rset = null;
		Long pollID1 = null;
		Long pollID2 = null;

		pst = con.prepareStatement(BAND_POLL, Statement.RETURN_GENERATED_KEYS);
		pst.executeUpdate();
		rset = pst.getGeneratedKeys();
		if (rset != null && rset.next()) {
			pollID1 = rset.getLong(1);
		}
		
		pst = con.prepareStatement(GAMING_POLL, Statement.RETURN_GENERATED_KEYS);
		pst.executeUpdate();
		rset = pst.getGeneratedKeys();
		if (rset != null && rset.next()) {
			pollID2 = rset.getLong(1);	
		}
		
		fillPollOptions(con, pollID1, pollID2);
	}

	/**
	 * Vraća imena trenutno postojećih tablica u našoj bazi podatataka.
	 * 
	 * @param cpds
	 *            {@link ComboPooledDataSource}
	 * @param con
	 *            {@link Connection}
	 * @return lista imena trenutno postojećih tablica u našoj bazi podataka
	 * @throws SQLException
	 */
	private List<String> getTableNames(ComboPooledDataSource cpds, Connection con) throws SQLException {
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getTables(null, null, null, new String[] { "TABLE" });
		List<String> tableNames = new ArrayList<>();

		while (res.next()) {
			tableNames.add(res.getString("TABLE_NAME"));
		}
		return tableNames;
	}

	/**
	 * Inicijalizira naziv baze podataka i bazen {@link Connection}-a. Minimalni
	 * broj je 5, maksimalni broj je 20. Ako zafali konecija stvara se novih 5.
	 * 
	 * @param cpds
	 *            {@link ComboPooledDataSource}
	 */
	private void initPool(ComboPooledDataSource cpds, ServletContextEvent sce) {
		Map<String, String> properties = getProperties(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"));
		String host = properties.get("host");
		String port = properties.get("port");
		String dbName = properties.get("name");
		String user = properties.get("user");
		String password = properties.get("password");
		String connectionURL = String.format("jdbc:derby://%s:%s/%s;user=%s;password=%s", host, port, dbName, user, password);
		cpds.setJdbcUrl(connectionURL);
	}

	/**
	 * Čita properties datoteke sa zadane putanje i vraća ih u novoj mapi.
	 * 
	 * @param fileName
	 *            putanja do properties datoteke
	 * @return sadržaj properties datoteke u obliku mape
	 */
	private Map<String, String> getProperties(String fileName) {
		Properties prop = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			prop.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		for (final Entry<Object, Object> entry : prop.entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		return map;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute(DPOOL);
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Zatvra primjerak {@link PreparedStatement}.
	 * 
	 * @param pst
	 *            {@link PreparedStatement}
	 */
	private void closeStatement(PreparedStatement pst) {
		try {
			pst.close();
		} catch (Exception ignorable) {
		}
	}

	/**
	 * Zatvara primjerak {@link Connection}-a.
	 * 
	 * @param con
	 *            {@link Connection}
	 */
	private void closeConnection(Connection con) {
		try {
			con.close();
		} catch (Exception ignorable) {
		}
	}

}