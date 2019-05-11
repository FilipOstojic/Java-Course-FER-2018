package hr.fer.zemris.java.hw06.demo4;

import java.util.Comparator;

/**
 * Razred <code>StudentRecord</code> predstavlja zapis o studentu ili kraće
 * record. Čuva osnovne podatke o studentu (ime, prezime, jmbag, ocijena).
 * 
 * @author Filip
 *
 */
public class StudentRecord {
	/**
	 * JMBAG studenta.
	 */
	private String jmbag;
	/**
	 * Ime studenta.
	 */
	private String firstName;
	/**
	 * Prezime studenta.
	 */
	private String lastName;
	/**
	 * Bodovi iz međuispita.
	 */
	private double mi;
	/**
	 * Bodovi iz završnog ispita.
	 */
	private double zi;
	/**
	 * Bodovi iz labosa.
	 */
	private double lab;
	/**
	 * Ocijena studenta.
	 */
	private int finalGrade;

	/**
	 * Konstruktor, prima: ime, prezime, jmbag, bodovi mi, bodovi zi, bodovi lab i
	 * ocjena studenta.
	 * 
	 * @param jmbag
	 *            jmbag studenta
	 * @param firstName
	 *            ime studenta
	 * @param lastName
	 *            prezime studenta
	 * @param mi
	 *            bodovi iz međuispita
	 * @param zi
	 *            bodovi iz završnog ispita
	 * @param lab
	 *            bodovi iz laboratorija
	 * @param finalGrade
	 *            ocijena studenta
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, double mi, double zi, double lab,
			int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mi = mi;
		this.zi = zi;
		this.lab = lab;
		this.finalGrade = finalGrade;
	}

	public static final Comparator<StudentRecord> BY_TOTAL_POINTS = (o1, o2) -> Double.compare(o2.getTotalPoints(),
			o1.getTotalPoints());
	public static final Comparator<StudentRecord> BY_JMBAG = (s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag());

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode(java.lang.Object)
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("%s %s %s %.2f %.2f %.2f %d", jmbag, lastName, firstName, mi, zi, lab, finalGrade);
	}

	/**
	 * Metoda vraća jmbag studenta.
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Metoda vraća ime studenta.
	 * 
	 * @return ime
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Metoda vraća prezime studenta.
	 * 
	 * @return prezime
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Metoda vraća broj bodova iz međuispita.
	 * 
	 * @return ocijena
	 */
	public double getMi() {
		return mi;
	}

	/**
	 * Metoda vraća broj bodova iz završnog ispita.
	 * 
	 * @return ocijena
	 */
	public double getZi() {
		return zi;
	}

	/**
	 * Metoda vraća broj bodova iz laboratorija.
	 * 
	 * @return ocijena
	 */
	public double getLab() {
		return lab;
	}

	/**
	 * Metoda vraća ocijenu studenta.
	 * 
	 * @return ocijena
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Metoda vraća ukupne bodove studenta.
	 * 
	 * @return ukupni bodovi
	 */
	public double getTotalPoints() {
		return getMi() + getZi() + getLab();
	}

}
