package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Razred koji dohvaća i postavlja primjerak razreda
 * {@link EntityManagerFactory}.
 * 
 * @author Filip
 *
 */
public class JPAEMFProvider {

	/**
	 * Konstanta, {@link EntityManagerFactory}.
	 */
	public static EntityManagerFactory emf;

	/**
	 * Getter za {@link EntityManagerFactory}
	 * 
	 * @return {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter za {@link EntityManagerFactory}
	 * 
	 * @param emf
	 *            {@link EntityManagerFactory}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}