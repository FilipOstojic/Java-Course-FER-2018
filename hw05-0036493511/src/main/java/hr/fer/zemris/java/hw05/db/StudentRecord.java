package hr.fer.zemris.java.hw05.db;

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
	 * Ocijena studenta.
	 */
	private String finalGrade;

	/**
	 * Konstruktor, prima ime, prezime, jmbag i ocjenu studenta.
	 * 
	 * @param jmbag
	 *            jmbag studenta
	 * @param firstName
	 *            ime studenta
	 * @param lastName
	 *            prezime studenta
	 * @param finalGrade
	 *            ocijena studenta
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, String finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

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
		return String.format("%12s %18s %12s %3s", jmbag, lastName, firstName, finalGrade);
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
	 * Metoda vraća ocijenu studenta.
	 * 
	 * @return ocijena
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
}
