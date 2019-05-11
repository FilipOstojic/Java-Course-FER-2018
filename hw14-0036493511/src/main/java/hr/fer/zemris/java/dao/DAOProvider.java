package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.dao.sql.SQLDAO;

/**
 * Singleton razred koji zna koga treba vratiti kao pružatelja usluge pristupa
 * podsustavu za perzistenciju podataka. Iako je odluka ovdje hardkodirana,
 * naziv razreda koji se stvara možemo dinamički pročitati iz konfiguracijske
 * datoteke i dinamički učitati.
 * 
 * @author Filip
 *
 */
public class DAOProvider {

	/**
	 * Konstanta, stvara primjerak razreda {@link SQLDAO}.
	 */
	private static DAO dao = new SQLDAO();

	/**
	 * Dohvat primjerka.
	 * 
	 * @return objekt koji enkapsulira pristup sloju za perzistenciju podataka.
	 */
	public static DAO getDao() {
		return dao;
	}

}